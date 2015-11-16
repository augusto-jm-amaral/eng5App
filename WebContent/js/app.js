angular.module('eng5App', ['ngRoute', 'ngResource'])
  .config(function ($routeProvider) {

    // CLIENTMAIN
    $routeProvider.when('/', {
      templateUrl: 'partials/mainCliente.html',
      controller: 'MainClienteController'
    });

    // EXIBEPRODUTO
    $routeProvider.when('/exibeproduto/:id', {
      templateUrl: 'partials/produto.html',
      controller: 'ProdutoController'
    });

    // EDITPRODUTO
    $routeProvider.when('/editproduto/:produtoId', {
      templateUrl: 'partials/novoproduto.html',
      controller: 'NovoProdutoController'
    });

    $routeProvider.when('/editproduto/', {
      templateUrl: 'partials/novoproduto.html',
      controller: 'NovoProdutoController'
    });

    // EDITCATEGORIA
    $routeProvider.when('/editcategoria/:categoriaId', {
      templateUrl: 'partials/novacategoria.html',
      controller: 'NovaCategoriaController'
    });

    $routeProvider.when('/editcategoria', {
      templateUrl: 'partials/novacategoria.html',
      controller: 'NovaCategoriaController'
    });

    // ADMIN MAIN
    $routeProvider.when('/admin', {
      templateUrl: 'partials/mainAdmin.html',
      controller: 'MainAdminController'
    });

    $routeProvider.otherwise({redirectTo: '/'});

  });
