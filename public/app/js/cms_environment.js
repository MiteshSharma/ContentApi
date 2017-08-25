(function() {
    'use strict';
    angular.module('cms.environment', [
      'ngResource',
      'cms.core'
    ])
    .factory('EnvironmentServer', ['EnvironmentApi', function(EnvironmentApi) {
        var EnvironmentServer = function(ctrl) {
          this.ctrl = ctrl;
        };

        EnvironmentServer.prototype.create = function(projectId, environment) {
          var promise, callback, failurecallback;
          promise = createEnvironment(projectId, environment).$promise;
          callback = createEnvironmentCallback(this.ctrl);
          failurecallback = createEnvironmentFailureCallback(this.ctrl);
          promise.then(callback, failurecallback);
        };

        function createEnvironment (projectId, environment) {
          return EnvironmentApi.create({projectId: projectId}, environment);
        }

        function createEnvironmentCallback(ctrl) {
          return function (response) {
            ctrl.onEnvironmentCreated(response);
          };
        };

        function createEnvironmentFailureCallback(ctrl) {
          return function (response) {
            ctrl.onEnvironmentCreateFailed();
          };
        };

        return EnvironmentServer;
    }])
    .factory('EnvironmentApi', [
      '$resource', 'urls','getHeaders',
      function ($resource, urls, getHeaders) {
        return $resource(
          [urls.baseApiUrl, 'project/:projectId/environment/'].join(''),
          { projectId: "@projectId" },
          {
            create: {
              method: 'POST',
              headers: getHeaders()
            }
          },
          {stripTrailingSlashes: false}
        );
      }
    ]);
})();