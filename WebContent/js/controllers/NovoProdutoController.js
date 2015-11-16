angular.module('eng5App').controller('NovoProdutoController',
  function ($scope, $routeParams, Produto, Categoria) {

    $scope.mensagem = {};

    if($routeParams.produtoId){
      carregaProduto();
    }else{
      $scope.produto = new Produto();
    }

    $scope.salva = function () {
      $scope.produto.$save()
        .then(function () {
          $scope.mensagem = {
            texto: "A produto foi salvo com sucesso"
          }
          $scope.produto = new Produto();
        })
        .catch(function (err) {
          $scope.mensagem = {
            texto: "Não foi possivel salvar o produto"
          }
        });
    };

    function carregaProduto() {
      Produto.get({id: $routeParams.produtoId},
        function (produto) {
          $scope.produto = produto;
        },
        function (err) {
          $scope.mensagem = {
            texto: 'Não foi possivel cadatrar o produto'
          }
          console.log(err);
        });
    };

    Categoria.query(function (categorias) {
      $scope.categorias = categorias;
    })

  });
