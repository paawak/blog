package com.swayam.demo.reactive.reactor3.react;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import org.junit.Test;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

public class FluxDeferGroupByBug {

	private static final Logger LOGGER = LoggerFactory.getLogger(FluxDeferGroupByBug.class);

	private static final long DELAY_MILLIS = 2_000;

	@Test
	public void testCreate() throws InterruptedException {

		// given
		Item[] testData = createTestData();

		Flux<Item> flux = Flux.create((FluxSink<Item> fluxSink) -> {

			for (int index = 0; index < testData.length; index++) {

				fluxSink.next(testData[index]);

				if (index == 3) {
					try {
						Thread.sleep(DELAY_MILLIS);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}

			fluxSink.complete();

		});

		// when
		boolean result = operateOnFlux(flux);

		// then
		assertTrue(result);

	}

	@Test
	public void testDeferBug() throws InterruptedException {

		// given
		Item[] testData = createTestData();

		Publisher<Item> publisher = (Subscriber<? super Item> lineItemRowSubscriber) -> {

			for (int index = 0; index < testData.length; index++) {

				lineItemRowSubscriber.onNext(testData[index]);

				if (index == 3) {
					try {
						Thread.sleep(DELAY_MILLIS);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}

			lineItemRowSubscriber.onComplete();

		};

		Flux<Item> flux = Flux.defer(() -> {
			return publisher;
		});

		// when
		boolean result = operateOnFlux(flux);

		// then
		assertTrue(result);

	}

	private boolean operateOnFlux(Flux<Item> flux) throws InterruptedException {

		CountDownLatch countDownLatch = new CountDownLatch(1);

		Mono<Map<Integer, List<Item>>> groupedByData = flux.collect(Collectors.groupingBy((Item row) -> {
			LOGGER.info("grouping by: {}", row);
			return row.getOrderKey();
		}));

		groupedByData.subscribe((Map<Integer, List<Item>> next) -> {
			LOGGER.info("next: {}", next);
			next.entrySet().parallelStream().map((Entry<Integer, List<Item>> entry) -> {
				int orderKey = entry.getKey();
				DoubleStream doubleStream = entry.getValue().parallelStream().mapToDouble((Item row) -> {
					return row.getExtendedPrice();
				});

				Item aggregatedRow = new Item();
				aggregatedRow.setOrderKey(orderKey);
				aggregatedRow.setExtendedPrice((float) doubleStream.sum());

				return aggregatedRow;
			}).forEach((Item row) -> {
				LOGGER.info("new aggregated event: {}", row);
			});

		}, (Throwable t) -> {
			countDownLatch.countDown();
			LOGGER.error("COMPLETED GROUP BY with error", t);
			fail("COMPLETED GROUP BY with error");
		}, () -> {
			LOGGER.info("COMPLETED GROUP BY");
			countDownLatch.countDown();
		});

		return countDownLatch.await(5, TimeUnit.SECONDS);

	}

	private Item[] createTestData() {
		Item lineItemRow_1 = new Item();
		lineItemRow_1.setOrderKey(1);
		lineItemRow_1.setExtendedPrice(20);

		Item lineItemRow_2 = new Item();
		lineItemRow_2.setOrderKey(2);
		lineItemRow_2.setExtendedPrice(30);

		Item lineItemRow_3 = new Item();
		lineItemRow_3.setOrderKey(2);
		lineItemRow_3.setExtendedPrice(40);

		Item lineItemRow_4 = new Item();
		lineItemRow_4.setOrderKey(1);
		lineItemRow_4.setExtendedPrice(15);

		Item lineItemRow_5 = new Item();
		lineItemRow_5.setOrderKey(1);
		lineItemRow_5.setExtendedPrice(55);

		Item lineItemRow_6 = new Item();
		lineItemRow_6.setOrderKey(2);
		lineItemRow_6.setExtendedPrice(65);

		return new Item[] { lineItemRow_1, lineItemRow_2, lineItemRow_3, lineItemRow_4, lineItemRow_5, lineItemRow_6 };

	}

	public static class Item {
		private int orderKey;
		private float extendedPrice;

		public int getOrderKey() {
			return orderKey;
		}

		public void setOrderKey(int orderKey) {
			this.orderKey = orderKey;
		}

		public float getExtendedPrice() {
			return extendedPrice;
		}

		public void setExtendedPrice(float extendedPrice) {
			this.extendedPrice = extendedPrice;
		}

		@Override
		public String toString() {
			return "Item [orderKey=" + orderKey + ", extendedPrice=" + extendedPrice + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + Float.floatToIntBits(extendedPrice);
			result = prime * result + orderKey;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Item other = (Item) obj;
			if (Float.floatToIntBits(extendedPrice) != Float.floatToIntBits(other.extendedPrice))
				return false;
			if (orderKey != other.orderKey)
				return false;
			return true;
		}
	}

}
