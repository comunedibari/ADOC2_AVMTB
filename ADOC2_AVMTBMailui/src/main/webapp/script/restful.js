
var userApp = angular.module("AngularUI", [ 
                                           'ui.bootstrap', 'ngRoute', 'ngResource', 'ng-context-menu', 
                                           'ngTable', 'ngGrid']);

userApp.config(function($routeProvider) {
	$routeProvider.when('/mailboxmessages/new', {
		controller : 'NewUserCtrl',
		templateUrl : 'views/newmailboxmessage.html'
	}).when('/mailboxmessages', {
		controller : 'UsersCtrl',
		templateUrl : 'views/mailboxmessages.html'
	}).when('/menu', {
		controller : 'MenuCtrl',
		templateUrl: 'menu.html'
	}).when('/mailboxmessages/clear', {
		controller : 'ClearMailboxMessageCtrl',
		templateUrl : 'views/clearmailboxmessage.html'
	})
});

userApp.controller('DropdownCtrl', function($scope) {

	$scope.items = [
	                "The first choice!",
	                "And another choice for you.",
	                "but wait! A third!"
	                ];

});

var myErrorMessage = null;

userApp.controller('ModalInstanceCtrl', function ($scope, $modalInstance, message, title) {

	$scope.message = message;
	$scope.title = title;

	$scope.ok = function () {
		$modalInstance.close();
	};

	$scope.cancel = function () {
		$modalInstance.dismiss('cancel');
	};
});



userApp.controller('PrincipalFunction', function($scope, $modal, $log, $timeout){
	$scope.open = function (size, message) {
		$scope.message = message;
		var modalInstance = $modal.open({
			templateUrl: 'myModalContent.html',
			controller: 'ModalInstanceCtrl',
			size: size,
			resolve: {
				message: function () {
					return $scope.message;
				}
			}
		});
		$timeout(function() {
			modalInstance.close();
		}, 2000);

	}; 

	$scope.waitMessage = function () {
		var modalInstance = $modal.open({
			templateUrl: 'myModalContent.html',
			controller: 'ModalInstanceCtrl',
			size: 'lg',
			resolve: {
				message: function () {
					return 'Operation in progress';
				},
				title: function () {
					return 'Attenzione';
				}
			}
		});
		$scope.waitWindow = modalInstance;
	};

	$scope.closeWaitMessage = function () {
		$scope.waitWindow.close();
	};

	$scope.infoMessage = function (message) {
		$scope.messageinfoToShow = message;
		var modalInstance = $modal.open({
			templateUrl: 'myModalContent.html',
			controller: 'ModalInstanceCtrl',
			size: 'sm',
			resolve: {
				message: function () {
					return $scope.messageinfoToShow;
				},
				title: function () {
					return 'Info';
				}
			}
		});
		$timeout(function() {
			modalInstance.close();					
		}, 3000);
	};

	$scope.errorMessage = function (message) {
		$scope.messageinfoToShow = message;
		var modalInstance = $modal.open({
			templateUrl: 'myModalContent.html',
			controller: 'ModalInstanceCtrl',
			size: 'sm',
			resolve: {
				message: function () {
					return $scope.messageinfoToShow;
				},
				title: function () {
					return 'Error';
				}
			}
		});
		$timeout(function() {
			modalInstance.close();					
		}, 3000);
	};


	$scope.showMessageTimeout = function(message, apply){
		$scope.message = message;
		$scope.showInfo = true;		
		$scope.showError = false;
		if (apply)
			$scope.$apply();
		$timeout(function() {
			$scope.showInfo = false;
			$scope.$apply();					
		}, 2000);
	}

	$scope.showErrorTimeout = function(error, apply){
		$scope.error = error;
		$scope.showInfo = false;		
		$scope.showError = true;
		if (apply)
			$scope.$apply();
		$timeout(function() {
			$scope.showError = false;
			if (apply)
				$scope.$apply();					
		}, 2000);
	}
});

userApp.controller('BeautifulTable', function($scope, $http, $modal, $log, $resource) {


	myScope = $scope;
	
	var Mailbox = $resource('angular/service/mailbox/loadAllMailBoxForConnectId');

	Mailbox.get(function(response){
		if (response.result == 'OK'){
			$scope.mailboxes = response.data;
		} else {
			$scope.open('sm', response.message);
		}	
	});
	
	$scope.status = [{"status":null, "idstatus":null},{"status":"operation_error", "idstatus":"operation_error"},{"status":"discharged", "idstatus":"discharged"},
	{"status":"force_operation", "idstatus":"force_operation"}, {"status":"operation_lock", "idstatus":"operation_lock"}];

	$scope.filterOptions = {
			filterText: "",
			useExternalFilter: false
	};
	$scope.totalServerItems = 0;
	$scope.pagingOptions = {
			pageSizes: [10, 20],
			pageSize: 10,
			currentPage: 1
	};  
	$scope.setPagingData = function(data, page, pageSize){	
		var pagedData = data.slice((page - 1) * pageSize, page * pageSize);
		$scope.myData = pagedData;
		//$scope.totalData = data;
		$scope.totalServerItems = data.length;
		if (!$scope.$$phase) {
			$scope.$apply();
		}
	};
	$scope.getPagedDataAsync = function (pageSize, page, searchText) {
		setTimeout(function () {
			var data;
			if (searchText) {
				var ft = searchText.toLowerCase();
				$scope.showPrincipalError = false;
				data = $scope.totalData.filter(function(item) {
					return JSON.stringify(item).toLowerCase().indexOf(ft) != -1;
				});
				$scope.setPagingData(data,page,pageSize);
				//$scope.setPagingData($scope.totalData,page,pageSize);
			} else {
				$scope.showPrincipalError = false;
				servizio.getMailboxMessageWithCallback( $scope, function(data){
					$scope.totalData = data;
					$scope.setPagingData(data,page,pageSize);
				});
			}
		}, 100);
	};

	$scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);

	$scope.$watch('pagingOptions', function (newVal, oldVal) {
		if (newVal !== oldVal && (newVal.currentPage !== oldVal.currentPage || newVal.pageSize !== oldVal.pageSize)) {
			$scope.setPagingData($scope.totalData,$scope.pagingOptions.currentPage,$scope.pagingOptions.pageSize);
		}
	}, true);
	$scope.$watch('filterOptions', function (newVal, oldVal) {
		if (newVal !== oldVal) {
			$scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
		}
	}, true);

	$scope.gridOptions = {
			data: 'myData',
			enablePaging: true,
			showFooter: true,
			showFilter: true,
			rowHeight: 60,
			showColumnMenu : true,
			enableColumnResize: true,
			enableRowSelection: false,
			columnDefs: [
			             {field: 'messageId', displayName: 'MessageId', headerCellTemplate:'views/header.html', cellTemplate: 'views/cellTemplate.html'},
			             {field: 'idMailbox', displayName: 'IdMailbox', headerCellTemplate:'views/header.html', cellTemplate: 'views/cellTemplate.html'},
			             {field: 'status', displayName: 'status', headerCellTemplate:'views/header.html', cellTemplate: 'views/cellTemplate.html'},
			             {field: 'statusMessage', displayName: 'statusMessage', headerCellTemplate:'views/header.html', cellTemplate: 'views/cellTemplate.html', visible: false},
			             {field: 'urlMime', displayName: 'urlMime', headerCellTemplate:'views/header.html', cellTemplate: 'views/cellTemplate.html', visible: false},
			             {field: 'dateDischarged', displayName: 'Date Discharged', headerCellTemplate:'views/header.html', cellTemplate: 'views/cellTemplate.html', visible: false},
			             {field: 'preview', displayName: '', headerCellTemplate:'views/header.html', cellTemplate: 'views/previewFileCellTemplate.html', width: 40}
			             ],             
			             totalServerItems:'totalServerItems',
			             pagingOptions: $scope.pagingOptions,
			             filterOptions: $scope.filterOptions
	};

	$scope.reloadTable = function(){
		if ($scope.filterOptions.filterText)
			$scope.filterOptions.filterText = '';
		else $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);
	}
	
	

	//$scope.greet = function($event) {
	//	alert($event);
	//};
});

userApp.controller('MenuController', ['$window', '$scope', function($window, $scope) {
	$scope.greet = function(clickEvent, idValue) {

		var idElement = clickEvent.currentTarget.id;
		var idRecord = $scope.row.getProperty('idUser');
		servizio.getMenuWithCallback( $scope, idRecord, function(data){
			menuItems = data;
			showMenu = true;
			for (i = 0; i<data.length; i++){
				$(document).contextmenu("setEntry", data[i].cmd, data[i].title, data[i].enabled);
				$(document).contextmenu("enableEntry", data[i].cmd, data[i].enabled);
			}
			var selector = "#" + idElement;
			var target = $(selector);
			$(document).contextmenu("open", target, {foo: "bar"});
		});
		//$window.alert('Hello ' + clickEvent.target);
	};
}]);

userApp.controller('PreviewFileCtrl', function ($scope, urlFile) {
	$scope.urlFile = urlFile;
});

userApp.controller('PeviewFileController', ['$window', '$scope', '$modal', function($window, $scope, $modal) {
	$scope.preview = function(clickEvent) {
		var idElement = clickEvent.currentTarget.id;
		$modal.open({
			templateUrl: 'previewFile.html',
			controller: 'PreviewFileCtrl',
			size: 'lg',
			resolve: {
				urlFile: function () {
					return $scope.row.getProperty('urlFile');
				}
			}
		});
	};

	$scope.download = function(clickEvent) {
		var urlMime = $scope.row.getProperty('urlMime');
		var idMailbox = $scope.row.getProperty('idMailbox');
		var messageId = $scope.row.getProperty('messageId');
		window.open(('download/service/mailboxmessage/download?urlMime=' + urlMime + '&idMailbox=' + idMailbox + '&messageId=' + messageId), 'downloadTarget', '');
	};
}]);


userApp.controller("UsersCtrl", [ '$scope','userservice', function($scope, userservice) {	
//	userservice.getUsers( $scope );	

} ]);

userApp.controller("NewUserCtrl", [ '$scope','$http','$timeout','userservice', function($scope,$http,$timeout, userservice) {				

	userservice.loadMailbox( $scope );	

	$scope.createNewUser = function(){
		var newuser = { 'nome':$scope.nome, 'cognome': $scope.cognome, 'indirizzo':$scope.indirizzo };
		// Call UserService to create a new user
		//
		userservice.createUser ( newuser, $scope );
	};

	$scope.fileupload = function(){
		var file = event.target.files[0];
		//iterate files since 'multiple' may be specified on the element
		//emit event upward
		$scope.fileSelected = file;
		$scope.toClear = event.target;
		//$scope.$emit("fileSelected", { file: file });
	}                 

	$scope.createNewEmail = function(){
		var eml = {'idMailbox': $scope.idMailbox};
		// Call UserService to create a new user
		//
		$scope.model = eml;
		$scope.file = $scope.fileSelected;
		userservice.createEmail($scope, $http, $timeout, eml);
	};	
} ]);

userApp.controller("ClearMailboxMessageCtrl", [ '$scope','$http','$timeout','userservice', function($scope,$http,$timeout, userservice) {				
	$scope.forceDelete = false;
	$scope.forceDeleteWithDelay = false;
	userservice.loadMailbox( $scope );	             
	$scope.onClick= function(item){
		if (item != undefined && $scope.forceDelete == item) {
			$scope.forceDeleteWithDelay = false;
		} else if (item != undefined && $scope.forceDeleteWithDelay == item) {
			$scope.forceDelete = false;
		}			
	};
	$scope.clearEmail = function(){
		if (!$scope.selectedItem || $scope.selectedItem == undefined){
			$scope.showErrorTimeout('Selezionare una mailbox');
			return;
		}
		userservice.clearEmail($scope, $http, $timeout, $scope.selectedItem, $scope.forceDelete, $scope.forceDeleteWithDelay, $scope.enableBackup, $scope.limit);
	};	
} ]);

userApp.controller("UsersByIdCtrl", [ '$scope','userservice', '$routeParams', function($scope, userservice, $routeParams) {	
	userservice.getUser($routeParams.userId, $scope);	
} ]);

userApp.factory( 'userservice', [ '$resource', function( $resource ){
	servizio = new User( $resource );
	return new User( $resource );
}] );


var servizio;
var myScope;

function User( resource ) {

	this.resource = resource; 


	this.clearEmail = function($scope, $http,$timeout, selectedMailbox, forceDelete, forceDeleteWithDelay, enableBackup, limit ) {
		
		$scope.waitMessage();
		var MailboxMessage = resource('angular/service/mailboxmessage/deleteMessage', {'limit': limit, 'forceDelete': forceDelete, 'forceDeleteWithDelay' : forceDeleteWithDelay, 'enableBackup': enableBackup} );	
		MailboxMessage.save(selectedMailbox, function(response){
			if (response.result == 'OK'){
				$scope.closeWaitMessage();
				$scope.infoMessage(response.message);
				$scope.selectedItem = '';
			} else {
				$scope.showErrorTimeout(response.message);
				$scope.closeWaitMessage();
			}	
		});

	}
	this.createEmail = function($scope, $http,$timeout, eml ) {
		var request = new XMLHttpRequest();
		request.open('POST', "download/service/mailboxmessage/upload");

		var formData = new FormData();
		if (!$scope.selectedItem || $scope.selectedItem == undefined){
			$scope.error = 'Selezionare una mailbox';
			$scope.showInfo = false;		
			$scope.showError = true;
			$scope.$apply();
			$timeout(function() {
				$scope.showError = false;
				$scope.$apply();					
			}, 2000);
			return;			
		}
		formData.append("idMailbox", $scope.selectedItem.idMailbox);
		formData.append("file", $scope.file);
		$scope.waitMessage();
		request.onreadystatechange=function() {
			if (request.readyState==4 && request.status==200) {
				$scope.closeWaitMessage();
				if (request.responseText == '') {
					$scope.infoMessage('Inserimento avvenuto con successo');	
				} else {
					$scope.errorMessage(request.responseText);
				}
			} else {
				$scope.closeWaitMessage();
				$scope.errorMessage('Impossibile effettuare l\'operazione');
			}
		};
		request.send(formData);	
	};

	this.loadMailbox = function( scope ) {
		//
		// Query Action Method
		//
		var Mailbox = resource('angular/service/mailbox/loadAllMailBoxForConnectId');

		Mailbox.get(function(response){
			if (response.result == 'OK'){
				scope.mailboxes = response.data;
			} else {
				scope.open('sm', response.message);
			}	
		});
	}

	this.getMailboxMessageWithCallback = function( scope, callback ) {
	
	var idMailbox;
	var idstatus;
	if (scope.selectedMailboxItem && scope.selectedMailboxItem.idMailbox)
		idMailbox = scope.selectedMailboxItem.idMailbox;
	if (scope.selectedStatusItem && scope.selectedStatusItem.idstatus)	
		idstatus = scope.selectedStatusItem.idstatus;
		//
		// Query Action Method
		//
		var MailboxMessages = resource('angular/service/mailboxmessage/loadAll');
		scope.waitMessage();
		MailboxMessages.get({"idMailbox":idMailbox,"idstatus":idstatus}, function(response){
			if (response.result == 'OK'){
				scope.closeWaitMessage();
//				scope.users = response.data;
				callback(response.data);
			} else {
				//scope.showPrincipalError = true;
				//scope.principalError = response.message;
//				errorMessage.showMessage('sm', response.message);
				scope.closeWaitMessage();
				scope.open('sm', response.message);
			}	
		});
	}


}

userApp.controller('DialogService', function($scope) {
	$dialogs.error('This is my error message');

});