(function() {
    'use strict';
    angular.module('cms.collectionField', [
      'ngResource',
      'cms.core'
    ])
    .factory('CollectionFieldServer', ['FieldApi', function(FieldApi) {
        var CollectionFieldServer = function(ctrl) {
          this.ctrl = ctrl;
        };

        CollectionFieldServer.prototype.create = function(projectId, collectionId, field) {
          var promise, callback, failurecallback;
          promise = createField(projectId, collectionId, field).$promise;
          callback = createFieldCallback(this.ctrl);
          failurecallback = createFieldFailureCallback(this.ctrl);
          promise.then(callback, failurecallback);
        };

        function createField (projectId, collectionId, field) {
          return FieldApi.create({projectId: projectId, collectionId: collectionId}, field);
        }

        function createFieldCallback(ctrl) {
          return function (response) {
            ctrl.onFieldCreated(response);
          };
        };

        function createFieldFailureCallback(ctrl) {
          return function (response) {
            ctrl.onFieldCreateFailed();
          };
        };

        CollectionFieldServer.prototype.update = function(projectId, collectionId, fieldId, field) {
          var promise, callback, failurecallback;
          promise = updateField(projectId, collectionId, fieldId, field).$promise;
          callback = updateFieldCallback(this.ctrl);
          failurecallback = updateFieldFailureCallback(this.ctrl);
          promise.then(callback, failurecallback);
        };

        function updateField (projectId, collectionId, fieldId, field) {
          return FieldApi.update(projectId, collectionId, fieldId, field);
        }

        function updateFieldCallback(ctrl) {
          return function (response) {
            console.log(response);
          };
        };

        function updateFieldFailureCallback(ctrl) {
          return function (response) {
            console.log(response);
          };
        };

        CollectionFieldServer.prototype.delete = function(projectId, collectionId, fieldId) {
          var promise, callback, failurecallback;
          promise = deleteField(projectId, collectionId, fieldId).$promise;
          callback = deleteFieldCallback(this.ctrl);
          failurecallback = deleteFieldFailureCallback(this.ctrl);
          promise.then(callback, failurecallback);
        };

        function deleteField (projectId, collectionId, fieldId) {
          return FieldApi.delete({projectId: projectId, collectionId: collectionId, fieldId: fieldId});
        }

        function deleteFieldCallback(ctrl) {
          return function (response) {
            ctrl.onFieldDeleted(response);
          };
        };

        function deleteFieldFailureCallback(ctrl) {
          return function (response) {
            //console.log(response);
          };
        };

        return CollectionFieldServer;

    }])
    .factory('FieldApi', [
      '$resource', 'urls','getHeaders',
      function ($resource, urls, getHeaders) {
        return $resource(
          [urls.baseApiUrl, 'project/:projectId/collection/:collectionId/field/:fieldId'].join(''),
          { projectId: "@projectId", collectionId: "@collectionId", fieldId: "@fieldId" },
          {
            create: {
              method: 'POST',
              headers: getHeaders()
            },
            update: {
              method: 'PUT',
              headers: getHeaders()
            },
            delete: {
              method: 'DELETE',
              headers: getHeaders()
            }
          },
          {stripTrailingSlashes: false}
        );
      }
    ]);
})();