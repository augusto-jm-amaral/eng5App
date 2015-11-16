angular.module('eng5App').factory('Categoria',
  function  ($resource) {
    return $resource('/eng5/categoria/:id');
});
