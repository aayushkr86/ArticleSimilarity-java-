Ext.require(['Ext.grid.*', 'Ext.data.*', 'Ext.form.*', 'Ext.layout.container.Column', 'Ext.tab.Panel']);
Ext.Loader.setConfig({
    enabled: true
});
Ext.tip.QuickTipManager.init();



var webColumns=[
         			{
         				header : 'PhraseG ID',
         				dataIndex : 'phraseGId',
         				sortable:true,
         				width:50
         			},
         			{
         				header : 'PhraseG',
         				dataIndex : 'phraseG',
         				sortable:true,
         				width    :150
         			},{
						header : 'Type',
         				dataIndex : 'type',
         				sortable:true,
         				width    :100
					},{
						header : 'No Type',
         				dataIndex : 'noType',
         				sortable:true,
         				width    :100
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
	var webSiteStore;
Ext.onReady(function () {

	var loadMask = new Ext.LoadMask(Ext.getBody(), {msg:"Loading"});
	loadMask.show();
	
	Ext.define('webModel',{
		extend : 'Ext.data.Model',
		fields : [ 
		          {name:'phraseGId', mapping:'phraseGId',type:'int'},
		           {name:'phraseG',mapping:'phraseG',type:'string'},
				   {name:'type',mapping:'type',type:'string'},
				   {name:'noType',mapping:'noType',type:'string'}
		         ]
	});

	webStore = Ext.create('Ext.data.Store', {
		id : 'webSiteStoreId',
		name : 'webSiteStoreName',
		model : 'webModel',
		proxy : {
			type : 'ajax',
			url :contextPath+'/review/viewPhraseG.do',
			extraParams:{
			},
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
						
				loadMask.hide();
			}
		},
		autoLoad : true
	});
	
	
	
	
	
	var webSiteTableGrid = Ext.create('Ext.grid.Panel', {
		title:'PhraseG Output',
		forceFit : true,
		id : 'webSiteGrid',
		store : webStore,
		columns : webColumns,
		width:200,
		height:300,
		autoFit : true,
		autoscroll:true,
		stripRows:true,
		renderTo : 'webSiteContainer',
		collapsible:true,
		overflowY:'auto'
	});

});
	
	
	
