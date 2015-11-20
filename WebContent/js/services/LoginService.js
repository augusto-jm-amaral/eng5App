angular.module('eng5App').factory('Login',
  function  ($resource) {
    return $resource('/eng5/login/');
});
