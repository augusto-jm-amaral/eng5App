angular.module('eng5App').factory('Produto',
  function  ($resource) {
    return $resource('/eng5/produto/:id');
});
