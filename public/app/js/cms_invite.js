(function() {
    'use strict';
    angular.module('cms.invite', [
      'ngResource',
      'cms.core'
    ])
    .factory('InviteServer', ['InviteApi', function(InviteApi) {
        var InviteServer = function(ctrl) {
          this.ctrl = ctrl;
        };

        InviteServer.prototype.create = function(projectId, inviteObj) {
          var promise, callback, failurecallback;
          promise = ceateInvite(projectId, inviteObj).$promise;
          callback = ceateInviteCallback(this.ctrl);
          failurecallback = ceateInviteFailureCallback(this.ctrl);
          promise.then(callback, failurecallback);
        };

        function ceateInvite (projectId, inviteObj) {
          return InviteApi.create({projectId: projectId},inviteObj);
        }

        function ceateInviteCallback(ctrl) {
          return function (response) {
              ctrl.onInviteCreated(response);
          };
        };

        function ceateInviteFailureCallback(ctrl) {
          return function (response) {
              ctrl.onInviteCreationFailed();
          };
        };
        return InviteServer;
    }])
    .factory('InviteApi', [
      '$resource', 'urls', 'getHeaders',
      function ($resource, urls, getHeaders) {
        return $resource(
          [urls.baseApiUrl, 'project/:projectId/invite/'].join(''),
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