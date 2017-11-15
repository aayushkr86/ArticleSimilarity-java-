Ext.require(['Ext.grid.*', 'Ext.data.*', 'Ext.form.*', 'Ext.layout.container.Column', 'Ext.tab.Panel']);
Ext.Loader.setConfig({
    enabled: true
});
Ext.tip.QuickTipManager.init();
var reviewColumns=[
         			{
         				header : 'Article Name',
         				dataIndex : 'articleName',
         				sortable:false,
         				width:100
         			},
         			 {
         				header : 'Article Desc',
         				dataIndex : 'articleDesc',
         				sortable:true,
         				width    :600,
         				renderer : function(value, metadata, record, rowIndex, colIndex, store) {
         					metadata.tdAttr = 'data-qtip="' + value + '"';
         					return value;
         				}
         			 }
         	    	];



var hideConfirmationMsg;
var showConfirmationMsg;
/* Hide the Confirmation Message */
	hideConfirmationMsg = function() {
		var confMsgDiv = Ext.get('confirmationMessage');
		confMsgDiv.dom.innerHTML = "";
		confMsgDiv.dom.style.display = 'none';
	}
	/* Show Confirmation Message */
	showConfirmationMsg = function(msg) {
		var confMsgDiv = Ext.get('confirmationMessage');
		confMsgDiv.dom.innerHTML =  msg;
		confMsgDiv.dom.style.display = 'inline-block';		
	}
	
Ext.onReady(function () {
   Ext.define('reviewModel', {
				extend : 'Ext.data.Model',
				fields : [ 
				           {name:'articleName', mapping:'articleName',type:'string'},
				           {name:'articleDesc', mapping:'articleDesc',type:'string'}
						  ]
			
			});
	
	var reviewStore = Ext.create('Ext.data.Store', {
				id : 'reviewStoreId',
				name : 'reviewStoreName',
				model : 'reviewModel',
				proxy : {
					type : 'ajax',
					url :contextPath+'/review/retriveAllReviews.do',
					actionMethods:{
						read:'POST'
					},
					reader : {
						type :'json',
						root:'model'
					}
				},
				listeners:
				{
					'load':function(store, records){
				}			
				},
				autoLoad : true
			});
	reviewStore.load();
	
	var reviewGrid = Ext.create('Ext.grid.Panel', {
		collapsible:true,
		title:'Articles Collected',
		forceFit : true,
		id : 'reviewGrid',
		store : reviewStore,
		columns :reviewColumns,
		height : 400,
		width : 800,
		autoFit : true,
		stripRows:true,
		renderTo : 'reviewGridContainer'
	});

});
	
	
	
