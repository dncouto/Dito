var app = angular.module("app",['services']);  

app.controller("browserController", ['$scope', '$ftHttp', function($scope, $ftHttp){  
	
	$ftHttp.get('browser/getAllProducts')
    .then(function (response) {
    	if (response.data.result === 'SUCCESS') {
            $scope.productList = response.data.msgSaida;
    	} else {
            var errorMsg = "";
            for (i = 0; i < response.data.error.length; i++) {
                errorMsg += response.data.error[i].message + '\n';
            }
            alert(errorMsg);
        }
    }, function (error) {
        if (error.status == 401)
            alert("Sua conexão expirou! Acesse o sistema novamente com seu usuário e senha...");
        else
            alert("Falha ao Comunicar com o Servidor! Verifique sua conexão ou a disponibilidade dos serviços...");
    });
     
}]);  