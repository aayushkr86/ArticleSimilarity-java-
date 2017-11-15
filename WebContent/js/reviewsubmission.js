Ext.require( [ 'Ext.grid.*', 'Ext.data.*', 'Ext.form.*',
		'Ext.layout.container.Column', 'Ext.tab.Panel' ]);

Ext.Loader.setConfig( {
	enabled : true
});
var loadMask;
var hideConfirmationMsg;
var showConfirmationMsg;
var contentPanel;
/* Hide the Confirmation Message */
hideConfirmationMsg = function() {
	var confMsgDiv = Ext.get('confirmationMessage');
	confMsgDiv.dom.innerHTML = "";
	confMsgDiv.dom.style.display = 'none';
}
/* Show Confirmation Message */
showConfirmationMsg = function(msg) {
	var confMsgDiv = Ext.get('confirmationMessage');
	confMsgDiv.dom.innerHTML = msg;
	confMsgDiv.dom.style.display = 'inline-block';
}
function generateJSONRequestForAddingReview()
{
	var reviewGen={};
	var articleDesc=Ext.getCmp('articleDesc').getValue();
	if(articleDesc)
	{
		reviewGen.articleDesc=articleDesc;
	}
	var articleName=Ext.getCmp('articleName').getValue();
	
	if(articleName!=null)
	{
		reviewGen.articleName=articleName;
	}
	return reviewGen;
}


function doReviewGenerationRequest(reviewGen, urlLink)
{
loadMask = new Ext.LoadMask(Ext.getBody(), {msg:"Loading"});
loadMask.show();
Ext.Ajax.request({	
method: 'POST',
processData: false,
contentType:'application/json',
jsonData: Ext.encode(reviewGen),
url:urlLink, 
success: function(response) {
var data;
if (response){
			 
			var JsonData = Ext.decode(response.responseText);
				if(JsonData.ebErrors != null){
					var errorObj=JsonData.ebErrors;
					for(i=0;i<errorObj.length;i++)
					{
							var value=errorObj[i].msg;
							showConfirmationMsg(value);
					}
					loadMask.hide();
				}
				else
				{
					var value=JsonData.message;
					showConfirmationMsg(value);
					loadMask.hide();
					contentPanel.hide();
					
				}
			}
},
failure : function(data) {
loadMask.hide();
}
});
}



Ext
		.onReady(function() {
			
				

			 contentPanel = Ext
					.create(
							'Ext.form.Panel',
							{
								title : 'Article Input',
								width : 800,
								height : 300,
								autoScroll : true,
								renderTo:'container',
								collapsible:true,
								defaults : {
										padding:'0 0 0 25',
									labelAlign : 'top'
								},
								layout : {
									type : 'table',
									columns : 1
								},
								items : [
										{
											xtype : 'textarea',
											fieldLabel : 'Article Desc',
											id : 'articleDesc',
											name : 'articleDesc',
											maxLength : 400,
											minLength : 150,
											width : 400,
											height:150,
											enforceMaxLength : true
											
										},
										{
											xtype : 'textfield',
											labelAlign : 'top',
											width : 150,
											fieldLabel : 'Article Name',
											id : 'articleName',
											name : 'articleName'
										},
										{
											xtype : 'button',
											text : 'Store Article',
											id : 'Save',
											disabled : false,
											padding:'0 25 25 -17',
											handler : function(store, btn, args) {

												var reviewGenFormat = generateJSONRequestForAddingReview();
												urlLink = contextPath + '/review/storereview.do';
												hideConfirmationMsg();
												doReviewGenerationRequest(reviewGenFormat,urlLink);
											}
										}]
							});
							
		
				
	
		});
		
		