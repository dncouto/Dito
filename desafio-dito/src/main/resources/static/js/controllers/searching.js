var app = angular.module("app",['services']);  

app.controller("searchingController", ['$scope', '$ftHttp', function($scope, $ftHttp){  
	
	 $scope.filterEvents = [];
	 $scope.selectedEvent = null;
      
     $scope.complete = function(string){  
    	 if (string.trim().length < 2) {
    		  $scope.filterEvents = [];
    		  $scope.selectedEvent = null;
    		  $scope.hidethis = true;
    		  return;
    	  }
	      $scope.hidethis = false;
	      
	      $ftHttp.post('http://localhost:8081/searchEvents', {eventName: string.toLowerCase()})
	      .then(function (response) {
	      	if (response.data.result === 'SUCCESS') {
	              $scope.filterEvents = response.data.msgSaida;
	      	} else {
	              var errorMsg = "";
	              for (i = 0; i < response.data.error.length; i++) {
	                  errorMsg += response.data.error[i].message + '\n';
	              }
	              console.log(errorMsg);
	          }
	      }, function (error) {
	          console.log("Falha ao Comunicar com o Servidor! Verifique sua conexão ou a disponibilidade dos serviços...");
	      });
     }  
      
     $scope.fillTextbox = function(event){  
           $scope.searching = event.eventName;
           $scope.selectedEvent = event;
           $scope.hidethis = true;  
     }
     
     $scope.openURL = function(event){
    	 if (event != null && event.url != null)
    		 window.location = event.url;
     }
}]);  