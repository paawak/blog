package com.swayam.demo.reactive.reactor1.react;

import com.swayam.demo.reactive.reactor1.model.LineItemRow;

import reactor.core.Environment;
import reactor.core.composable.spec.DeferredStreamSpec;
import reactor.core.composable.spec.Streams;
import reactor.event.dispatch.Dispatcher;

public class ReactMain {

    public static void main(String[] a) {

	Environment env = new Environment();
	// Broadcaster<String> sink = Broadcaster.create(Environment.get());

	DeferredStreamSpec<LineItemRow> stream = Streams.defer();
	Dispatcher dispatcher = env.getDefaultDispatcher();

    }

}
