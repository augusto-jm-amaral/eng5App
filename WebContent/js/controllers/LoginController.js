angular.module('eng5App').controller('LoginController',
  function ($scope, $location, Login) {

    $scope.showMenu = false;
    $scope.mensagem = {};
    $scope.usuario = new Login();

    $scope.login = function () {
      $scope.usuario.$save()
        .then(function (sucess) {
          $location.path(sucess.path);
        })
        .catch(function (err) {
          $scope.mensagem = err.data;
        });
    };

  });
