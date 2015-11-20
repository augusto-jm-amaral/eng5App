angular.module('eng5App', ['ngRoute', 'ngResource'])
  .config(function ($routeProvider, $httpProvider) {

    $httpProvider.interceptors.push('NaoAutorizado');

    // CLIENTMAIN
    $routeProvider.when('/', {
      templateUrl: 'partials/mainCliente.html',
      controller: 'MainClienteController'
    });

    // EXIBEPRODUTO
    $routeProvider.when('/exibeproduto/:produtoId', {
      templateUrl: 'partials/produto.html',
      controller: 'ProdutoController'
    });

    // EDITPRODUTO
    $routeProvider.when('/editproduto/:produtoId', {
      templateUrl: 'partialsAdmin/novoproduto.html',
      controller: 'NovoProdutoController'
    });

    $routeProvider.when('/editproduto/', {
      templateUrl: 'partialsAdmin/novoproduto.html',
      controller: 'NovoProdutoController'
    });

    // EDITCATEGORIA
    $routeProvider.when('/editcategoria/:categoriaId', {
      templateUrl: 'partialsAdmin/novacategoria.html',
      controller: 'NovaCategoriaController'
    });

    $routeProvider.when('/editcategoria', {
      templateUrl: 'partialsAdmin/novacategoria.html',
      controller: 'NovaCategoriaController'
    });

    // ADMIN MAIN
    $routeProvider.when('/admin', {
      templateUrl: 'partialsAdmin/mainAdmin.html',
      controller: 'MainAdminController'
    });

    // LOGIN
    $routeProvider.when('/login', {
      templateUrl: 'partials/login.html',
      controller: 'LoginController'
    });

    // QUEM SOMOS
    $routeProvider.when('/quemSomos', {
      templateUrl: 'partials/quemSomos.html',
    });

    $routeProvider.otherwise({redirectTo: '/'});

  });
