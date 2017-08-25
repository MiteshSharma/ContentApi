(function() {
    'use strict';
	angular.module('cms.registration', [
	  'ngResource',
	  'ngCookies',
	  'cms.core'
	])
	.factory('RegistrationServer', ['$cookies', 'LoginApi', 'PROJECT', function($cookies, LoginApi, PROJECT) {
	    var RegistrationServer = function(ctrl) {
	      this.ctrl = ctrl;
	    };

	    RegistrationServer.prototype.login = function(user) {
	      var promise, callback, failurecallback;
	      promise = loginUser(user).$promise;
	      callback = loginUserCallback(this.ctrl);
	      failurecallback = loginUserFailureCallback(this.ctrl);
	      promise.then(callback, failurecallback);
	    };

	    function loginUser (user) {
	      user.projectId = PROJECT.projectId;
	      user.type = PROJECT.type;
	      return LoginApi.login(user);
	    }

	    function loginUserCallback(ctrl) {
			return function (response) {
		        $cookies.put('Authorization', response.token);
	      		ctrl.onLoginSuccess();
		    };
	    };

	    function loginUserFailureCallback(ctrl) {
	      	return function (response) {
	      		ctrl.onLoginFailed();
		    };
	    };

	    RegistrationServer.prototype.logout = function() {
	      var promise, callback, failurecallback;
	      promise = logoutUser().$promise;
	      callback = logoutUserCallback(this.ctrl);
	      failurecallback = logoutUserFailureCallback(this.ctrl);
	      promise.then(callback, failurecallback);
	    };

	    function logoutUser () {
	      return LoginApi.logout();
	    }

	    function logoutUserCallback(ctrl) {
	      return function (response) {
	      	$cookies.put('Authorization', null);
		  	ctrl.onLogoutComplete();
		  };
	    };

	    function logoutUserFailureCallback(ctrl) {
	      return function (response) {
	      	ctrl.onLogoutFalied();
		  };
	    };

	    return RegistrationServer;
	}])
	.factory('LoginApi', [
	  '$resource', 'urls','getHeaders',
	  function ($resource, urls, getHeaders) {
	    return $resource(
	      [urls.baseApiUrl, 'userRegister'].join(''),
	      { },
	      {
	          login: {
	            method: 'POST'
	          },
	          logout: {
	            method: 'DELETE',
	            headers: getHeaders()
	          }
	      },
	      {stripTrailingSlashes: false}
	    );
	  }
	]);
})();