
angular.module('eng5App').controller('UsuarioController',
  function ($scope, Login) {

    $scope.mensagemRecep = undefined;
    $scope.userName = '';
    $scope.menuLogin = {
      text: 'Login',
      url: '/eng5/#/login'
    }

    Login.get({},function (usuario) {
      $scope.userName = usuario.userName;
      $scope.menuLogin = {
        text: ' Logout',
        url: '/eng5/#/logout'
      }
      $scope.mensagemRecep = 'Olá ' + usuario.userName;
    });

  });
