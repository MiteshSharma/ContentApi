(function() {
    'use strict';
    angular.module('cms.collection', [
      'ngResource',
      'cms.core'
    ])
    .factory('CollectionServer', ['CollectionApi', function(CollectionApi) {
        var CollectionServer = function(ctrl) {
          this.ctrl = ctrl;
        };

        CollectionServer.prototype.create = function(projectId, collection) {
          var promise, callback, failurecallback;
          promise = createCollection(projectId, collection).$promise;
          callback = createCollectionCallback(this.ctrl);
          failurecallback = createCollectionFailureCallback(this.ctrl);
          promise.then(callback, failurecallback);
        };

        function createCollection (projectId, collection) {
          return CollectionApi.create({projectId: projectId}, collection);
        }

        function createCollectionCallback(ctrl) {
          return function (response) {
            ctrl.onCollectionCreated(response);
          };
        };

        function createCollectionFailureCallback(ctrl) {
          return function (response) {
            ctrl.onCollectionCreateFailed();
          };
        };

        CollectionServer.prototype.update = function(projectId, collectionId, collection) {
          var promise, callback, failurecallback;
          promise = updateCollection(projectId, collectionId, collection).$promise;
          callback = updateCollectionCallback(this.ctrl);
          failurecallback = updateCollectionFailureCallback(this.ctrl);
          promise.then(callback, failurecallback);
        };

        function updateCollection (projectId, collectionId, collection) {
          return CollectionApi.update({projectId: projectId, collectionId: collectionId}, collection);
        }

        function updateCollectionCallback(ctrl) {
          return function (response) {
            console.log(response);
          };
        };

        function updateCollectionFailureCallback(ctrl) {
          return function (response) {
            console.log(response);
          };
        };

        CollectionServer.prototype.get = function(projectId, collectionId) {
          var promise, callback, failurecallback;
          promise = getCollection(projectId, collectionId).$promise;
          callback = getCollectionCallback(this.ctrl);
          failurecallback = getCollectionFailureCallback(this.ctrl);
          promise.then(callback, failurecallback);
        };

        function getCollection (projectId, collectionId) {
          return CollectionApi.get({'projectId': projectId, 'collectionId': collectionId});
        }

        function getCollectionCallback(ctrl) {
          return function (response) {
            ctrl.onCollectionFetched(response);
          };
        };

        function getCollectionFailureCallback(ctrl) {
          return function (response) {
            ctrl.onCollectionFetchError();
          };
        };

        CollectionServer.prototype.getAll = function(projectId) {
          var promise, callback, failurecallback;
          promise = getCollections(projectId).$promise;
          callback = getCollectionsCallback(this.ctrl);
          failurecallback = getCollectionsFailureCallback(this.ctrl);
          promise.then(callback, failurecallback);
        };

        function getCollections (projectId) {
          return CollectionApi.getAll({'projectId': projectId});
        }

        function getCollectionsCallback(ctrl) {
          return function (response) {
            ctrl.onCollectionFetched(response);
          };
        };

        function getCollectionsFailureCallback(ctrl) {
          return function (response) {
            ctrl.onCollectionFetchError();
          };
        };

        return CollectionServer;
    }])
    .factory('CollectionApi', [
      '$resource', 'urls','getHeaders',
      function ($resource, urls, getHeaders) {
        return $resource(
          [urls.baseApiUrl, 'project/:projectId/collection/:collectionId'].join(''),
          { projectId: "@projectId", collectionId: "@collectionId" },
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
              headers: getHeaders(),
              isArray: true
            }
          },
          {stripTrailingSlashes: false}
        );
      }
    ]);
})();