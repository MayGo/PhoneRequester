<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<!DOCTYPE HTML>
<html>
<head>

<title></title>

<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<link rel="stylesheet" href="<c:url value='/css/main.css'/>"
	type="text/css">

<script src="<c:url value='/js/jquery-1.6.1.min.js'/>"></script>
<script src="<c:url value='/js/jquery.periodicalupdater.js'/>"></script>
<script src="<c:url value='/js/jquery.tmpl.js'/>"></script>

</head>
<body>
	<div class="container">
		<script id="phoneReqTemplate" type="text/x-jquery-tmpl">
		<div class="phoneMain">
			<div class="pRow">
				<span class="pLabel">Päringu id:</span><span class="pValue">{{= reqNr}}</span>
			</div>
			<div class="pRow">
				<span class="pLabel">Telefon:</span><span class="pValue">{{= phoneNr}}</span>
			</div>
			{{if active}} 
			<div class="pRow">
				<span class="pLabel">Keel:</span><span class="pValue">{{= lang}}</span>
			</div>
			<div class="pRow">
				<span>Teenus aktiivne kuni {{= activeUntil}} {{= activeUntilTime}}</span>
			</div>
			<div class="pRow">
				<span>XL-teenus {{if xlStatus}}aktiivne {{= xlActiveSince}} - {{= xlActiveUntil}} {{= xlLang}} keeles.{{else}}mitteaktiivne.{{/if}}</span>
			</div>
			{{else}}<div class="pRow">Teenus mitteaktiivne</div>
			{{/if}}
				{{if overrideNrs}}
				<div class="pOverride"><span>Välja arvatud:</span>
					<table class="table">
						<tr><th>Nimi</th><th>Number</th></tr>
				    {{each overrideNrs}}
						<tr><td>{{= $index}}</td><td>{{= $value}}</td></tr>
					{{/each}}
					</table> 
				</div>
				{{/if}}
			
	
		</div>
</script>
		<div id="content" class="prepend-1 span-22 append-1 prepend-top last"></div>
	</div>

	<script type="text/javascript">
		$.PeriodicalUpdater('<c:url value="/ajax"/>', {
			method : 'get', // method; get or post
			data : '', // array of values to be passed to the page - e.g. {name: "John", greeting: "hello"}
			minTimeout : 5000, // starting value for the timeout in milliseconds
			maxTimeout : 20000, // maximum length of time between requests
			multiplier : 1, // the amount to expand the timeout by if the response hasn't changed (up to maxTimeout)
			type : 'text', // response type - text, xml, json, etc. See $.ajax config options
			maxCalls : 0, // maximum number of calls. 0 = no limit.
			autoStop : 10,
			dataType : 'json'
		// automatically stop requests after this many returns of the same data. 0 = disabled.
		}, function(remoteData, success, xhr, handle) {
			
			//remove last template object
			$(".phoneMain").slideUp(2000, function() {
				$(this).remove();
			}); 
			//append new template obj
			$("#phoneReqTemplate").tmpl(remoteData).appendTo($("#content"));
			
		});
	</script>

</body>
</html>
