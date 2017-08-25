(function() {
    'use strict';
    angular.module('cms.collectionContent', [
      'ngResource',
      'cms.core'
    ])
    .factory('CollectionContentServer', ['CollectionContentApi', function(CollectionContentApi) {
        var CollectionContentServer = function(ctrl) {
          this.ctrl = ctrl;
        };

        CollectionContentServer.prototype.create = function(projectId, collectionId, envId, content) {
          var promise, callback, failurecallback;
          promise = createContent(projectId, collectionId, envId, content).$promise;
          callback = createContentCallback(this.ctrl);
          failurecallback = createContentFailureCallback(this.ctrl);
          promise.then(callback, failurecallback);
        };

        function createContent (projectId, collectionId, envId, content) {
          return CollectionContentApi.create({projectId: projectId, collectionId: collectionId, envId: envId}, content);
        }

        function createContentCallback(ctrl) {
          return function (response) {
            ctrl.onContentCreated(response);
          };
        };

        function createContentFailureCallback(ctrl) {
          return function (response) {
            ctrl.onContentCreateFailed();
          };
        };

        CollectionContentServer.prototype.update = function(projectId, collectionId, envId, contentId, content) {
          var promise, callback, failurecallback;
          promise = updateContent(projectId, collectionId, envId, contentId, content).$promise;
          callback = updateContentCallback(this.ctrl);
          failurecallback = updateContentFailureCallback(this.ctrl);
          promise.then(callback, failurecallback);
        };

        function updateContent (projectId, collectionId, envId, contentId, content) {
          return CollectionContentApi.update({projectId: projectId, collectionId: collectionId, envId: envId, contentId: contentId}, content);
        }

        function updateContentCallback(ctrl) {
          return function (response) {
            console.log(response);
          };
        };

        function updateContentFailureCallback(ctrl) {
          return function (response) {
            console.log(response);
          };
        };

        CollectionContentServer.prototype.get = function(projectId, collectionId, envId, contentId) {
          var promise, callback, failurecallback;
          promise = getContent(projectId, collectionId, envId, contentId).$promise;
          callback = getContentCallback(this.ctrl);
          failurecallback = getContentFailureCallback(this.ctrl);
          promise.then(callback, failurecallback);
        };

        function getContent (projectId, collectionId, envId, contentId) {
          return CollectionContentApi.get(projectId, collectionId, envId, contentId);
        }

        function getContentCallback(ctrl) {
          return function (response) {
            console.log(response);
          };
        };

        function getContentFailureCallback(ctrl) {
          return function (response) {
            console.log(response);
          };
        };

        CollectionContentServer.prototype.getAll = function(projectId, collectionId, envId) {
          var promise, callback, failurecallback;
          promise = getContents(projectId, collectionId, envId).$promise;
          callback = getContentsCallback(this.ctrl);
          failurecallback = getContentsFailureCallback(this.ctrl);
          promise.then(callback, failurecallback);
        };

        function getContents (projectId, collectionId, envId) {
          return CollectionContentApi.getAll({projectId: projectId, collectionId: collectionId, envId: envId});
        }

        function getContentsCallback(ctrl) {
          return function (response) {
            console.log(response);
            ctrl.onCollectionContentFetched(response);
          };
        };

        function getContentsFailureCallback(ctrl) {
          return function (response) {
            ctrl.onCollectionContentFetchError();
          };
        };

        return CollectionContentServer;
    }])
    .factory('CollectionContentApi', [
      '$resource', 'urls','getHeaders',
      function ($resource, urls, getHeaders) {
        return $resource(
          [urls.baseApiUrl, 'project/:projectId/collection/:collectionId/env/:envId/content/:contentId'].join(''),
          { projectId: "@projectId", collectionId: "@collectionId", envId: "@envId", contentId: "@contentId" },
          {
            create: {
              method: 'POST',
              headers: getHeaders()
            },
            update: {
              method: 'PUT',
              headers: getHeaders()
            },
            get: {
              method: 'GET',
              headers: getHeaders()
            },
            getAll: {
              method: 'GET',
              isArray: true,
              headers: getHeaders()
            }
          },
          {stripTrailingSlashes: false}
        );
      }
    ]);
})();