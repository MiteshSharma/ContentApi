(function() {
    'use strict';
    angular.module('cms.media', [
      'ngResource',
      'cms.core'
    ])
    .factory('MediaServer', ['MediaApi', function(MediaApi) {
        var MediaServer = function(ctrl) {
          this.ctrl = ctrl;
        };

        MediaServer.prototype.create = function(projectId, media) {
          var promise, callback, failurecallback;
          promise = ceateMedia(projectId, media).$promise;
          callback = ceateMediaCallback(this.ctrl);
          failurecallback = ceateMediaFailureCallback(this.ctrl);
          promise.then(callback, failurecallback);
        };

        function ceateMedia (projectId, media) {
          return MediaApi.create({projectId: projectId},media);
        }

        function ceateMediaCallback(ctrl) {
          return function (response) {
              ctrl.onMediaCreated(response);
          };
        };

        function ceateMediaFailureCallback(ctrl) {
          return function (response) {
              ctrl.onMediaCreationFailed();
          };
        };

        MediaServer.prototype.getAll = function(projectId) {
          var promise, callback, failurecallback;
          promise = getMedia(projectId).$promise;
          callback = getMediaCallback(this.ctrl);
          failurecallback = getMediaFailureCallback(this.ctrl);
          promise.then(callback, failurecallback);
        };

        function getMedia (projectId) {
          return MediaApi.get({projectId: projectId});
        }

        function getMediaCallback(ctrl) {
          return function (response) {
              ctrl.onMediaFetched(response);
          };
        };

        function getMediaFailureCallback(ctrl) {
          return function (response) {
              ctrl.onMediaFetchError();
          };
        };
        return MediaServer;
    }])
    .factory('MediaApi', [
      '$resource', 'urls', 'getHeaders',
      function ($resource, urls, getHeaders) {
        return $resource(
          [urls.baseApiUrl, 'project/:projectId/media/'].join(''),
          { projectId: "@projectId" },
          {
            create: {
              method: 'POST',
              headers: getHeaders()
            },
            get: {
              method: 'GET',
              headers: getHeaders(),
              isArray: true,
            }
          },
          {stripTrailingSlashes: false}
        );
      }
    ]);
})();