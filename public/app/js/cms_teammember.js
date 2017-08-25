(function() {
    'use strict';
	angular.module('cms.teamMember', [
	  'ngResource',
	  'cms.core'
	])
	.factory('TeamMemberServer', ['TeamMemberApi', function(TeamMemberApi) {
	    var TeamMemberServer = function(ctrl) {
	      this.ctrl = ctrl;
	    };

	    TeamMemberServer.prototype.add = function(teamId, teamMember) {
	      var promise, callback, failurecallback;
	      promise = addTeamMember(teamId, teamMember).$promise;
	      callback = addTeamMemberCallback(this.ctrl);
	      failurecallback = addTeamMemberFailureCallback(this.ctrl);
	      promise.then(callback, failurecallback);
	    };

	    function addTeamMember (teamId, teamMember) {
	      return TeamMemberApi.add(teamId, teamMember);
	    }

	    function addTeamMemberCallback(ctrl) {
			return function (response) {
	      		console.log(response);
		    };
	    };

	    function addTeamMemberFailureCallback(ctrl) {
	      	return function (response) {
	      		console.log(response);
		    };
	    };

	    TeamMemberServer.prototype.kick = function(teamId, teamMemberId) {
	      var promise, callback, failurecallback;
	      promise = kickTeamMember(teamId, teamMemberId).$promise;
	      callback = kickTeamMemberCallback(this.ctrl);
	      failurecallback = kickTeamMemberFailureCallback(this.ctrl);
	      promise.then(callback, failurecallback);
	    };

	    function kickTeamMember (teamId, teamMember) {
	      return TeamMemberApi.kick(teamId, teamMember);
	    }

	    function kickTeamMemberCallback(ctrl) {
			return function (response) {
	      		console.log(response);
		    };
	    };

	    function kickTeamMemberFailureCallback(ctrl) {
	      	return function (response) {
	      		console.log(response);
		    };
	    };

	    return TeamMemberServer;
	}])
	.factory('TeamMemberApi', [
	  '$resource', 'urls','getHeaders',
	  function ($resource, urls, getHeaders) {
	    return $resource(
	      [urls.baseApiUrl, 'team/:teamId/member/:memberId'].join(''),
	      { teamId: "@teamId", memberId: "@memberId" }
	      {
	          add: {
	            method: 'POST',
	            headers: getHeaders()
	          },
	          kick: {
	            method: 'DELETE',
	            headers: getHeaders()
	          }
	      },
	      {stripTrailingSlashes: false}
	    );
	  }
	]);
})();