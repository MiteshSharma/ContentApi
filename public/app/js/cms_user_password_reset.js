(function() {
    'use strict';
	angular.module('cms.userPasswordReset', [
	  'ngResource',
	  'cms.core'
	])
	.factory('UserPasswordResetServer', ['UserPasswordResetApi', 'PROJECT', function(UserPasswordResetApi, PROJECT) {
	    var UserPasswordResetServer = function(ctrl) {
	      this.ctrl = ctrl;
	    };

	    UserPasswordResetServer.prototype.reset = function(email) {
	      var promise, callback, failurecallback;
	      promise = resetPassword(email).$promise;
	      callback = resetPasswordCallback(this.ctrl);
	      failurecallback = resetPasswordFailureCallback(this.ctrl);
	      promise.then(callback, failurecallback);
	    };

	    function resetPassword (email) {
	      return UserPasswordResetApi.reset({email: email, projectId: PROJECT.projectId});
	    }

	    function resetPasswordCallback(ctrl) {
			return function (response) {
	      		ctrl.onPasswordResetSuccess();
		    };
	    };

	    function resetPasswordFailureCallback(ctrl) {
	      	return function (response) {
	      		ctrl.onPasswordResetFailed();
		    };
	    };

	    return UserPasswordResetServer;
	}])
	.factory('UserPasswordResetApi', [
	  '$resource', 'urls','getHeaders',
	  function ($resource, urls, getHeaders) {
	    return $resource(
	      [urls.baseApiUrl, 'userPasswordReset/:email'].join(''),
	      { email: '@email' },
	      {
	          reset: {
	            method: 'POST',
	            headers: getHeaders()
	          }
	      },
	      {stripTrailingSlashes: false}
	    );
	  }
	]);
})();