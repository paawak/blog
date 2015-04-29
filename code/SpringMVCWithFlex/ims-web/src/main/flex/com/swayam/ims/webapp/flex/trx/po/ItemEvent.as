package com.swayam.ims.webapp.flex.trx.po {
	
	import flash.events.Event;
	
	public class ItemEvent extends Event {
		
		public static const EVENT_ITEM_ADDED:String = "ItemAdded";
		
		private var _itemId:Number;
		var _itemName:String;
		var _price:Number;
		var _qty:int;
		var _batchNo:String;
		var _manufactureDate:Date;
		var _expiryDate:Date;
		
		public function ItemEvent(eventType:String, _itemId:Number) {
			super(eventType);
			this._itemId = _itemId;
		}
		
		public function get itemId():Number {
			return _itemId;
		}
		
//		function set itemName(_itemName:String):void {
//			this._itemName = _itemName;
//		}
		
		public function get itemName():String {
			return _itemName;
		}
		
//		function set price(_price:Number):void {
//			this._price = _price;
//		}
		
		public function get price():Number {
			return _price;
		}
		
//		function set qty(_price:int):void {
//			this._qty = _qty;
//		}
		
		public function get qty():int {
			return _qty;
		}
		
//		function set batchNo(_batchNo:String):void {
//			this._batchNo = _batchNo;
//		}
		
		public function get batchNo():String {
			return _batchNo;
		}
		
//		function set manufactureDate(_manufactureDate:Date):void {
//			this._manufactureDate = _manufactureDate;
//		}
		
		public function get manufactureDate():Date {
			return _manufactureDate;
		}
		
//		function set expiryDate(_expiryDate:Date):void {
//			this._expiryDate = _expiryDate;
//		}
		
		public function get expiryDate():Date {
			return _expiryDate;
		}
		
	}
	
}
