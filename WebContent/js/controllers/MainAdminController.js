angular.module('eng5App').controller('MainAdminController',
  function ($scope, Produto) {

    $scope.produtos = [];
    $scope.filtro = '';
    $scope.mensagem = {};

    function carregaProdutos() {

      Produto.query(function (produtos) {
        $scope.produtos = produtos;
        $scope.mensagem = {};
      },
      function (err) {
        $scope.mensagem = {
          texto: "Não foi possivel carregar os produto, tente atualizar a pagina"
        };
        console.log(err);
      });

    }

    carregaProdutos();

    $scope.remove = function (produto) {
      Produto.delete({id: produto.id},
        carregaProdutos,
        function (err) {
          $scope.mensagem = {
            texto: "Não foi possive deletar o produto, tente novamente"
          };
          console.log(err);
        });
    }

  });
