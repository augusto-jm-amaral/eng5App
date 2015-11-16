angular.module('eng5App').controller('MainClienteController',
  function ($scope, Produto, Categoria) {

    $scope.filtro = "";
    $scope.produtos = [];
    $scope.categorias = [];

    function carregarProdutos() {
      Produto.query(function (produtos) {
        $scope.produtos = produtos;
      });
    };

    function carregarCategorias() {
      Categoria.query(function (categorias) {
          $scope.categorias = categorias;
      });
    };

    $scope.filtrarPorCategoria = function(categoria) {
      $scope.filtro = categoria.nome;
    };

    carregarProdutos();
    carregarCategorias();

  });
