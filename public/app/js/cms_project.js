(function() {
    'use strict';
	angular.module('cms.project', [
	  'ngResource',
	  'cms.core'
	])
	.factory('ProjectServer', ['ProjectApi', '$cookies', function(ProjectApi, $cookies) {
	    var ProjectServer = function(ctrl) {
	      this.ctrl = ctrl;
	    };

	    ProjectServer.prototype.create = function(project) {
	      var promise, callback, failurecallback;
	      promise = createProject(project).$promise;
	      callback = createProjectCallback(this.ctrl);
	      failurecallback = createProjectFailureCallback(this.ctrl);
	      promise.then(callback, failurecallback);
	    };

	    function createProject (project) {
	      return ProjectApi.create(project);
	    }

	    function createProjectCallback(ctrl) {
	      return function (response) {
	        if (response.token) {
	            $cookies.put('Authorization', response.token);
	        }
	      	ctrl.onProjectCreated(response.projectObject);
		  };
	    };

	    function createProjectFailureCallback(ctrl) {
	      return function (response) {
	      	ctrl.onProjectCreateFailed();
		  };
	    };

	    ProjectServer.prototype.update = function(project) {
	      var promise, callback, failurecallback;
		  promise = updateProject(project.id, project).$promise;
		  callback = updateProjectCallback(this.ctrl);
		  failurecallback = updateProjectFailureCallback(this.ctrl);
		  promise.then(callback, failurecallback);
	    };

	    function updateProject (projectId, project) {
	      return ProjectApi.update({projectId: projectId}, project);
	    }

	    function updateProjectCallback(ctrl) {
	      return function (response) {
	      	ctrl.onProjectUpdated(response);
		  };
	    };

	    function updateProjectFailureCallback(ctrl) {
	      return function (response) {
	      	ctrl.onProjectUpdateFailed();
		  };
	    };

	    ProjectServer.prototype.get = function(projectId) {
	      var promise, callback, failurecallback;
	      promise = getProject(projectId).$promise;
	      callback = getProjectCallback(this.ctrl);
	      failurecallback = getProjectFailureCallback(this.ctrl);
	      promise.then(callback, failurecallback);
	    };

	    function getProject (projectId) {
	      return ProjectApi.get({projectId: projectId});
	    }

	    function getProjectCallback(ctrl) {
	      return function (response) {
	      	ctrl.onProjectFetched(response);
		  };
	    };

	    function getProjectFailureCallback(ctrl) {
	      return function (response) {
	      	ctrl.onProjectFetchFailed();
		  };
	    };

	    ProjectServer.prototype.getAll = function() {
	      var promise, callback, failurecallback;
	      promise = getProjects().$promise;
	      callback = getProjectsCallback(this.ctrl);
	      failurecallback = getProjectsFailureCallback(this.ctrl);
	      promise.then(callback, failurecallback);
	    };

	    function getProjects () {
	      return ProjectApi.getAll();
	    }

	    function getProjectsCallback(ctrl) {
	      return function (response) {
	      	ctrl.onProjectsFetched(response);
		  };
	    };

	    function getProjectsFailureCallback(ctrl) {
	      return function (response) {
	      	ctrl.onProjectsFetchFailed();
		  };
	    };

	    return ProjectServer;

	}])
	.factory('ProjectApi', [
	  '$resource', 'urls','getHeaders',
	  function ($resource, urls, getHeaders) {
	    return $resource(
	      [urls.baseApiUrl, 'project/:projectId'].join(''),
	      { projectId: "@projectId" },
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