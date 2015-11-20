angular.module('eng5App').controller('ProdutoController',
  function ($scope, $routeParams, Produto) {

    $scope.produto = {};

    function carregaProduto() {
      Produto.get({id: $routeParams.produtoId},
        function (produto) {
          $scope.produto = produto;
        },function (err) {

          console.log(err);
        });

    };

    carregaProduto();

    $scope.comprar = function () {
      console.log("Comprar");
    };

  });
