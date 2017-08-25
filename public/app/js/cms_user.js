(function() {
    'use strict';
    angular.module('cms.user', [
      'ngResource',
      'cms.core'
    ])
    .factory('UserServer', ['UserApi', '$cookies', 'PROJECT', function(UserApi, $cookies, PROJECT) {
        var UserServer = function(ctrl) {
          this.ctrl = ctrl;
        };

        UserServer.prototype.register = function(user) {
          var promise, callback, failurecallback;
          promise = registerUser(user).$promise;
          callback = registerUserCallback(this.ctrl);
          failurecallback = registerUserFailureCallback(this.ctrl);
          promise.then(callback, failurecallback);
        };

        function registerUser (user) {
          user.projectId = PROJECT.projectId;
          user.type = PROJECT.type;
          return UserApi.create(user);
        }

        function registerUserCallback(ctrl) {
          return function (response) {
              $cookies.put('Authorization', response.token);
              ctrl.onRegisterSuccess(response.isExist);
          };
        };

        function registerUserFailureCallback(ctrl) {
          return function (response) {
              ctrl.onRegisterFail();
          };
        };

        UserServer.prototype.get = function() {
          var promise, callback, failurecallback;
          promise = getUser().$promise;
          callback = getUserCallback(this.ctrl);
          failurecallback = getUserFailureCallback(this.ctrl);
          promise.then(callback, failurecallback);
        };

        function getUser () {
          return UserApi.get({'Authorization': $cookies.get('Authorization')});
        }

        function getUserCallback(ctrl) {
          return function (response) {
              ctrl.onUserFetched(response);
          };
        };

        function getUserFailureCallback(ctrl) {
          return function (response) {
              ctrl.onUserFetchError();
          };
        };

        UserServer.prototype.update = function(user) {
          var promise, callback, failurecallback;
          promise = updateUser(user).$promise;
          callback = updateUserCallback(this.ctrl);
          failurecallback = updateUserFailureCallback(this.ctrl);
          promise.then(callback, failurecallback);
        };

        function updateUser (user) {
          return UserApi.update(user);
        }

        function updateUserCallback(ctrl) {
          return function (response) {
              ctrl.onUserUpdated(response);
          };
        };

        function updateUserFailureCallback(ctrl) {
          return function (response) {
              ctrl.onUserUpdateFailed();
          };
        };
        return UserServer;
    }])
    .factory('UserApi', [
      '$resource', 'urls', '$cookies', 'getHeaders',
      function ($resource, urls, $cookies, getHeaders) {
        return $resource(
          [urls.baseApiUrl, 'user'].join(''),
          { },
          {
            create: {
              method: 'POST'
            },
            update: {
              method: 'PUT',
              headers: getHeaders()
            },
            get: {
              method: 'GET',
              headers: {'Authorization': $cookies.get('Authorization')}
            }
          },
          {stripTrailingSlashes: false}
        );
      }
    ]);
})();