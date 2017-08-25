(function() {
    'use strict';
    angular.module('cms.projectKey', [
      'ngResource',
      'cms.core'
    ])
    .factory('ProjectKeyServer', ['ProjectKeyApi', function(ProjectKeyApi) {
        var ProjectKeyServer = function(ctrl) {
          this.ctrl = ctrl;
        };

        ProjectKeyServer.prototype.get = function(projectId, environmentId) {
          var promise, callback, failurecallback;
          promise = getProjectKey(projectId, environmentId).$promise;
          callback = getProjectKeyCallback(this.ctrl);
          failurecallback = getProjectKeyFailureCallback(this.ctrl);
          promise.then(callback, failurecallback);
        };

        function getProjectKey (projectId, environmentId) {
          return ProjectKeyApi.get({projectId: projectId, environmentId: environmentId});
        }

        function getProjectKeyCallback(ctrl) {
          return function (response) {
              ctrl.onProjectKeyFetched(response);
          };
        };

        function getProjectKeyFailureCallback(ctrl) {
          return function (response) {
              ctrl.onProjectKeyFetchError();
          };
        };
        return ProjectKeyServer;
    }])
    .factory('ProjectKeyApi', [
      '$resource', 'urls', 'getHeaders',
      function ($resource, urls, getHeaders) {
        return $resource(
          [urls.baseApiUrl, 'project/:projectId/env/:environmentId/key/'].join(''),
          { projectId: "@projectId", environmentId: "@environmentId" },
          {
            get: {
              method: 'GET',
              headers: getHeaders()
            }
          },
          {stripTrailingSlashes: false}
        );
      }
    ]);
})();