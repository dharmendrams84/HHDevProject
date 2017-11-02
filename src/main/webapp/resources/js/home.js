var dataType = function(id, name) {
	this.id = ko.observable(id);
	this.name = ko.observable(name);
}

ko.extenders.numeric = function(target, precision) {
	// create a writable computed observable to intercept writes to our
	// observable
	var result = ko.computed(
			{
				read : target, // always return the original observable's value
				write : function(newValue) {
					var current = target(), roundingMultiplier = Math.pow(10,
							precision), newValueAsNum = isNaN(newValue) ? 0
							: parseFloat(+newValue), valueToWrite = Math
							.round(newValueAsNum * roundingMultiplier)
							/ roundingMultiplier;

					// only write if it changed
					if (valueToWrite !== current) {
						target(valueToWrite);
					} else {
						// if the rounded value is the same, but a different
						// value was written, force a notification for the
						// current field
						if (newValue !== current) {
							target.notifySubscribers(valueToWrite);
						}
					}
				}
			}).extend({
		notify : 'always'
	});

	// initialize with current value to make sure it is rounded appropriately
	result(target());

	// return the new computed observable
	return result;
};

function syncOrders() {

	if ("" != $("#orderNos").val().trim()) {
		if (confirm("Are you sure you want to sync the order(s): "
				+ $("#orderNos").val() + "?")) {
			$.ajax({
				contentType : 'application/json; charset=UTF-8',
				url : "syncOrders?orderNos=" + $("#orderNos").val(),
				type : "GET",
				success : function(data) {
					alert(data);
					$("#orderNos").val("").focus();
				},
				error : function(err) {
					alert("Error");

					$('<iframe>', {
						src : 'http://google.com',
						id : 'myFrame',
						frameborder : 0,
						scrolling : 'no'
					}).appendTo('.accordion');
				}

			});
		}
	} else {
		alert("Please enter order no(s)");
		$("#orderNos").val("").focus();
	}
};

function resetOrders() {
	if ("" != $("#resetOrderNos").val().trim()) {
		if ("select" != $("#orderStatus").val().trim()) {
			if (confirm("Are you sure you want to reset the order(s): "
					+ $("#resetOrderNos").val() + " with status "+ $("#orderStatus").val() +"?")) {
				$.ajax({
					contentType : 'application/json; charset=UTF-8',
					url : "resetOrders?orderNos=" + $("#resetOrderNos").val() + "&orderStatus=" + $("#orderStatus").val(),
					type : "GET",
					success : function(data) {
						alert(data);
						$("#resetOrderNos").val("").focus();
						$("#orderStatus").prop('selectedIndex',0);
					},
					error : function(err) {
						alert("Error");
	
						$('<iframe>', {
							src : 'http://google.com',
							id : 'myFrame',
							frameborder : 0,
							scrolling : 'no'
						}).appendTo('.accordion');
					}
				});
			}
		} else{
			alert("Please select order status");
			$("#orderStatus").prop('selectedIndex',0).focus();
		}
	} else {
		alert("Please enter order no(s)");
		$("#resetOrderNos").val("").focus();
	}
};

function uploadStoreDetails() {
	if ("" != $("#siteId").val().trim()) {
		if ("" != $("#filePath").val().trim()) {
			if (confirm("Are you sure you want to upload the store details for siteId: " + $("#siteId").val() + "?")) {
				$.ajax({
					contentType : 'application/json; charset=UTF-8',
					url : "uploadStoreDetails?siteId=" + $("#siteId").val() + "&filePath=" + $("#filePath").val(),
					type : "GET",
					success : function(data) {
						alert(data);
						$("#siteId").val("").focus();
						$("#filePath").val("");
					},
					error : function(err) {
						alert("Error");
					}
				});
			}
		} else {
			alert("Please enter file path");
			$("#filePath").val("").focus();
		}
	} else {
		alert("Please enter siteId");
		$("#siteId").val("").focus();
	}
};

function setReadOnly(elementId) {
	$("#" + elementId).prop('readonly', true);
}

function ondbClick1() {
	$("#cnfOrderNo").prop('readonly', false);
	var OriginalContent = $("#cnfOrderNo").text();
	$("#cnfOrderNo").html(
			"<input type='text' value='" + OriginalContent + "' />");
	$("#cnfOrderNo").children().first().focus();

	$("#cnfOrderNo").children().first().keypress(function(e) {
		if (e.which == 13) {
			var newContent = $("#cnfOrderNo").val();
			$("#cnfOrderNo").parent().text(newContent);
		}
	});
}

function ondbClick2() {

	$("#cnfSku").prop('readonly', false);
	var OriginalContent = $("#cnfSku").text();
	$("#cnfSku").html("<input type='text' value='" + OriginalContent + "' />");
	$("#cnfSku").children().first().focus();

	$("#cnfSku").children().first().keypress(function(e) {
		if (e.which == 13) {
			var newContent = $("#cnfSku").val();
			$("#cnfSku").parent().text(newContent);
		}
	});
}

function ondbClick3() {

	$("#cnfTrackingNo").prop('readonly', false);
	var OriginalContent = $("#cnfTrackingNo").text();
	$("#cnfTrackingNo").html(
			"<input type='text' value='" + OriginalContent + "' />");
	$("#cnfTrackingNo").children().first().focus();

	$("#cnfTrackingNo").children().first().keypress(function(e) {
		if (e.which == 13) {
			var newContent = $("#cnfTrackingNo").val();
			$("#cnfTrackingNo").parent().text(newContent);
		}
	});
}

function ondbClick4() {

	$("#cnfPaymentTransId").prop('readonly', false);
	var OriginalContent = $("#cnfPaymentTransId").text();
	$("#cnfPaymentTransId").html(
			"<input type='text' value='" + OriginalContent + "' />");
	$("#cnfPaymentTransId").children().first().focus();

	$("#cnfPaymentTransId").children().first().keypress(function(e) {
		if (e.which == 13) {
			var newContent = $("#cnfPaymentTransId").val();
			$("#cnfPaymentTransId").parent().text(newContent);
		}
	});
}

function markFieldsAsReadOnlyTrue() {
	$("#cnfOrderNo").prop('readonly', true);
	$("#cnfSku").prop('readonly', true);
	$("#cnfTrackingNo").prop('readonly', true);
	$("#cnfPaymentTransId").prop('readonly', true);
}

function confirmPaymentTransId() {

	if ("" != $("#orderNo").val().trim() && "" != $("#sku").val().trim()
			&& "" != $("#trackingNo").val().trim()
			&& "" != $("#paymentTransId").val().trim()) {
		$("#reviewDetailsDiv").show();

		$("#orderNo").prop('readonly', true);
		$("#sku").prop('readonly', true);
		$("#trackingNo").prop('readonly', true);
		$("#paymentTransId").prop('readonly', true);

		$("#cnfOrderNo").val($("#orderNo").val().trim());
		$("#cnfSku").val($("#sku").val().trim());
		$("#cnfTrackingNo").val($("#trackingNo").val().trim());
		$("#cnfPaymentTransId").val($("#paymentTransId").val().trim());
		
		$("#cnfOrderNo").prop('readonly', true);
		$("#cnfSku").prop('readonly', true);
		$("#cnfTrackingNo").prop('readonly', true);
		$("#cnfPaymentTransId").prop('readonly', true);

	} else {
		alert("Please enter the required values");
	}
}

function updatePaymentTransId() {

	$("#cnfOrderNo").prop('readonly', true);
	$("#cnfSku").prop('readonly', true);
	$("#cnfTrackingNo").prop('readonly', true);
	$("#cnfPaymentTransId").prop('readonly', true);

	if ("" != $("#cnfOrderNo").val().trim() || "" != $("#cnfSku").val().trim()
			|| "" != $("#cnfTrackingNo").val().trim()
			|| "" != $("#cnfPaymentTransId").val().trim()) {
		if (confirm("Are you sure you want to Update the Payment Transaction Id?")) {
			$.ajax({
				contentType : 'application/json; charset=UTF-8',
				url : "updatePaymentTransId?orderNo=" + $("#cnfOrderNo").val()
						+ "&sku=" + $("#cnfSku").val() + "&trackingNo="
						+ $("#cnfTrackingNo").val() + "&paymentTransId="
						+ $("#cnfPaymentTransId").val(),
				type : "GET",
				success : function(data) {
					alert(data);
					$("#orderNo").val("");
					$("#sku").val("");
					$("#trackingNo").val("");
					$("#paymentTransId").val("");

					$("#cnfOrderNo").val("");
					$("#cnfSku").val("");
					$("#cnfTrackingNo").val("");
					$("#cnfPaymentTransId").val("");

					$("#reviewDetailsDiv").css("display", "none");

					$("#orderNo").prop('readonly', false);
					$("#sku").prop('readonly', false);
					$("#trackingNo").prop('readonly', false);
					$("#paymentTransId").prop('readonly', false);
				},
				error : function(err) {
					alert("Error");

					$('<iframe>', {
						src : 'http://google.com',
						id : 'myFrame',
						frameborder : 0,
						scrolling : 'no'
					}).appendTo('.accordion');
				}

			});
		}
	} else {
		alert("Please enter the required values");
		//$("#resetOrderNos").val("").focus();
	}
};

self.updateReturns = function() {
	if ("" != $("#returnNos").val().trim()) {
		if (confirm("Are you sure you want to reset the order(s): "
				+ $("#returnNos").val() + "?")) {
			$.ajax({
				contentType : 'application/json; charset=UTF-8',
				url : "updateReturns?returnNos=" + $("#returnNos").val(),
				type : "GET",
				success : function(data) {
					alert(data);
					$("#returnNos").val("").focus();
				},
				error : function(err) {
					alert("Error");

					$('<iframe>', {
						src : 'http://google.com',
						id : 'myFrame',
						frameborder : 0,
						scrolling : 'no'
					}).appendTo('.accordion');
				}
			});
		}
	} else {
		alert("Please enter Return no(s)");
		$("#returnNos").val("").focus();
	}
};

function getLogFileContent(batchNo) {

	$.ajax({
		contentType : 'application/json; charset=UTF-8',
		url : "downloadLogFile?batchNo=" + batchNo,
		type : "GET",
		//dataType : "text/plain",
		success : function(data) {
			alert("Success");
			console.log("downloaded log");
		},
		error : function(err) {
			alert("Error");

			$('<iframe>', {
				src : 'http://google.com',
				id : 'myFrame',
				frameborder : 0,
				scrolling : 'no'
			}).appendTo('.accordion');
		}

	});
};

var homeViewModel = function() {
	var self = this;
	self.buildVersion = ko.observable();
	self.selectedTab = ko.observable();

	self.newsletterSuccess = ko.observable();
	self.newsletterError = ko.observable();
	self.emailInvalidMessage = ko.observable();
	self.noNewsletterData = ko.observable();
	
	self.runjobOption = ko.observable().extend({
		required : true
	});
	self.startDate = ko.observable().extend({
		required : true
	});
	self.endDate = ko.observable();
	self.clickRunJob = function() {
		if (self.runjobOption == 1) {
			// alert(1);
		}
	}

	self.getVersion = function() {
		// alert("getting evrsion");
		$.ajax({
			url : "version",
			type : "GET",
			dataType : "text",
			success : function(data) {
				self.buildVersion(data);
			}
		});
	};

	self.logFileContent = ko.observable();

	self.hideOrderTable = function() {
		$("#nonSyncOrderTable").hide();
		$("#nonSyncOrderTable_wrapper").hide();
		$("#fromOrder").val("").focus();
		$("#toOrder").val("");
	}

	self.fetchNonSyncOrders = function() {
		$("#nonSyncOrderTable").show();
		var $table = $('#nonSyncOrderTable').dataTable(
				{

					"bProcessing" : true,
					"bServerSide" : true,
					"bDestroy" : true,
					"bFilter" : true,
					"bSort" : false,
					"bInfo" : false,
					"sAjaxSource" : "getNonSyncOrders?fromOrder="
							+ $("#fromOrder").val() + "&toOrder="
							+ $("#toOrder").val(),
					"sSearch" : true,

					"aoColumns" : [ {
						"mData" : "id.mzOrderNo"
					}, {
						"mData" : "id.orderSubmitDate",
						"mRender" : function(data, type, row) {
							return parseDateTime(data);
						}
					}, {
						"mData" : "id.buyerName"
					}, {
						"mData" : "id.buyerEmail"
					}, {
						"mData" : "id.buyerPhone"
					}, {
						"mData" : "id.orderStatus"
					} ]
				});
		$table.fnDraw();
	};

	//Show all campaigns saved so far
	self.fetchOMSJobHistory = function() {

		var $table = $('#omsHistoryTable').dataTable({

			"bProcessing" : true,
			"bServerSide" : true,
			"bDestroy" : true,
			"bFilter" : true,
			"bSort" : false,
			"bInfo" : false,
			"sAjaxSource" : "fetchJobHistory",
			"sSearch" : true,

			"aoColumns" : [ {
				"mData" : "jobId"
			}, {
				"mData" : "batchNo"
			}, {
				"mData" : "entityType"
			}, {
				"mData" : "jobStartDate",
				"mRender" : function(data, type, row) {
					return parseDateTime(data);
				}
			}, {
				"mData" : "jobEndDate",
				"mRender" : function(data, type, row) {
					return parseDateTime(data);
				}
			}, {
				"mData" : "dataFetchDate",
				"mRender" : function(data, type, row) {
					return parseDateTime(data);
				}
			}, {
				"mData" : "transferStatus"
			}, {
				"mData" : "jobRunStatus"
			} ]
		});
		$table.fnDraw();
	};

	self.getVersion();

	// END FETCH JOB HISTORY
}

function unixToHumanTime(data) {
	return moment.unix(data / 1000).format("YYYY-MM-DD HH:mm:ss")
}

function parseDateTime(data) {
	return moment(data).format("MM-DD-YYYY HH:mm:ss")
}

function closeError() {
	$("#serverError").hide();
}

ko.bindingHandlers.money = {
	update : function(element, valueAccessor, allBindingsAccessor) {
		var value = valueAccessor(), allBindings = allBindingsAccessor();
		var valueUnwrapped = ko.utils.unwrapObservable(value);

		var m = "";
		if (valueUnwrapped) {
			m = numeral(valueUnwrapped).format('$0,0.00');
		}
		$(element).text(m);
	}
};

var displaySpinner = true;
var displayError = true;

$(document).ajaxError(function(event, jqxhr, settings, exception) {
	if (jqxhr.status >= 200 && jqxhr.status <= 300)
		return;

	if (displayError) {
		if (jqxhr.responseJSON != null)
			$("#serverErrorMessage").html(jqxhr.responseJSON.message);
		else if (jqxhr.responseText != null)
			$("#serverErrorMessage").html(jqxhr.responseText);
		else {
			$("#serverErrorMessage").html(jqxhr.statusText);
		}
		$("#serverError").show();
	}
});

var viewModel;

$(function() {

	function closeError() {
		$("#serverError").hide();
	}

	$.ajaxPrefilter(function(options, originalOptions, jqXHR) {
		$("#serverError").hide();
		if (displaySpinner)
			$("#progressIndicator").show();
		jqXHR.complete(function() {
			if (displaySpinner)
				$("#progressIndicator").hide();
			displaySpinner = true;
		});

	});

	$(".tabs a").click(function(e) {
		var tabElement = e.target.parentElement;
		var newTab = e.target;
		var parent = tabElement.parentElement;
		var activeTab = $(parent).find('.active');
		var activeTabId = activeTab.data('tab-id');
		var newTabId = $(newTab).data('tab-id');
		var hideSave = $(newTab).data('hide-save');

		if (activeTabId == newTabId)
			return;
		viewModel.selectedTab(newTabId);
		activeTab.removeClass('active');
		$(newTab).addClass('active');

		$('#' + activeTabId).fadeOut('fast', function() {
			$('#' + newTabId).fadeIn('fast');
		});

		if (hideSave) {
			$("#saveBtn").hide();
		} else {
			$("#saveBtn").show();
		}

	});

	$(".subTabs span").click(function(e) {
		var tabElement = e.target.parentElement;
		var newTab = e.target;
		var parent = tabElement.parentElement;
		var activeTab = $(parent).find('.selected');
		var activeTabId = activeTab.data('tab-id');
		var newTabId = $(newTab).data('tab-id');

		activeTab.removeClass('selected');
		$(newTab).addClass('selected');

		if (activeTabId != null) {
			$('#' + activeTabId).fadeOut('fast', function() {
				$('#' + newTabId).fadeIn('fast');
			});
		} else {
			$('#' + newTabId).fadeIn('fast');
		}

	});

	viewModel = new homeViewModel();
	viewModel.newsletterSuccess(false);
	viewModel.newsletterError(false);
	viewModel.emailInvalidMessage(false);
	viewModel.noNewsletterData(false);
	ko.applyBindings(viewModel);

	$('#fetchNewsletterSignupList').on('click', function(e) {
		var emailRegEx = new RegExp(/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))$/i);
		if(emailRegEx.test($('#emailAddress').val())) {
			$.ajax({
				url : "fetchNewsletterSignupList",
				type : "POST",
				data: {
					emailAddress: $('#emailAddress').val()
				},
				success : function(response) {
					console.log(response);
					var emailSent = response.successMessage;
					var customerCount = response.customerCount;
					
					if(customerCount > 0) {
						viewModel.newsletterSuccess(true);
						viewModel.newsletterError(false);
						viewModel.emailInvalidMessage(false);
						viewModel.noNewsletterData(false);
					}
					else {
						viewModel.noNewsletterData(true);
						viewModel.newsletterSuccess(false);
						viewModel.newsletterError(false);
						viewModel.emailInvalidMessage(false);
					}
				},
				error : function(err) {
					console.log(err);
					viewModel.newsletterSuccess(false);
					viewModel.newsletterError(true);
					viewModel.emailInvalidMessage(false);
					viewModel.noNewsletterData(false);
				}
			});
		}
		else {
			viewModel.emailInvalidMessage(true);
		}
	});
	
	$("#saveBtn").hide();

	$("#jobDetail").off();
	$("#jobDetail").on();
	$("#jobDetail").on('click', function(e) {
		console.log("#ShowJobDetails");
	});
});