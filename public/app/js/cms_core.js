(function() {
    'use strict';
      angular.module('cms.core', ['ngCookies'])
        .constant('urls', {
            baseApiUrl: 'http://localhost:9000/api/v1/'
        })
        .constant('PROJECT', {projectId: "1", type: "Normal"})
        .factory('getHeaders', [
	      '$cookies',
	      function ($cookies) {
	        return function () {
	          return {
            		'Authorization': $cookies.get('Authorization')
          		};
	        };
	      }
	    ]).factory('getAuthorizationToken', [
        '$cookies',
        function ($cookies) {
          return function () {
            return $cookies.get('Authorization');
          };
        }
      ]);
})();