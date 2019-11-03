/**
 * Serviços genéricos para a aplicação.
 */
angular.module('services', []);

/**
 * [$ftHttp]
 * Encapsulamento do $http.
 */
angular.module('services').service('$ftHttp', ['$http', '$q', function($http, $q){

    function print(msg, error) {
		if(error) throw new Error('[$ftHttp] ' + msg);
		console.log('[$ftHttp] ' + msg);
	}

	// $http.error handler
	function error(resp){
        return $q.reject(resp);
    }
    // $http.error handler with redirect to login
    function errorWithRedirect(resp){
        if (resp.status === 401) {
			localStorage.removeItem('token');
            window.location = "/login?msg=Sua conexão expirou! Acesse o sistema novamente com seu usuário e senha...";
		}
		return $q.reject(resp);
	}

	// Chama o $http
    function doVerb(method, url, data, opt, step) {
        // Cria cabeçalho
        if (!opt) opt = {};

        if (!opt.headers) opt.headers = { };
        // Insere token
        if (!opt.anonymous) {
            opt.headers['Authorization'] = 'CustomToken ' + localStorage.getItem('token');
        }
        
        // Parametriza os verbos http
		opt.method = method;
        opt.url = url;

		if(data)
			opt.data = data;
		if(step && typeof step === 'function')
			opt.uploadEventHandlers = {
				progress: function(e) {
					if (e.lengthComputable) step(Math.round(e.loaded * 100 / e.total));
				}
            };
        
        if (opt.redirect401 == undefined || opt.redirect401 == null) 
            opt.redirect401 = true;
        if (!opt.redirect401) {
            return $http(opt).then(null, error);
        }
		return $http(opt).then(null, errorWithRedirect);
	}


    // VERBOS HTTP
	this.get = function(url, opt, step){
        return doVerb('GET', url, null, opt, step);
    };
    this.put = function (url, data, opt, step) {
        return doVerb('PUT', url, data, opt, step);
    };
    this.post = function (url, data, opt, step) {
        return doVerb('POST', url, data, opt, step);
    };
    this.delete = function (url, opt, step) {
        return doVerb('DELETE', url, null, opt, step);
    };
}]);
