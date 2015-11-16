angular.module('eng5App').controller('NovaCategoriaController',
  function ($scope, $routeParams, Categoria) {

    $scope.mensagem = {};
    $scope.filtro = '';

    if($routeParams.categoriaId){
      carregarCategoria();
    }else{
      $scope.categoria = new Categoria();
    }

    $scope.salva = function () {
      $scope.categoria.$save()
        .then(function () {
          $scope.mensagem = {
            texto: "A categoria foi salvo com sucesso"
          }
          $scope.categoria = new Categoria();
          carregarCategorias();
        })
        .catch(function (err) {
          $scope.mensagem = {
            texto: "Não foi possivel salvar a categoria"
          }
          console.log(err);
        });
    };

    function carregaCategoria() {
      Categoria.get({id: $routeParams.categoriaId},
        function (categoria) {
          $scope.categoria = categoria;
        },
        function (err) {
          $scope.mensagem = {
            texto: 'Não foi possivel obter o contato'
          }
          console.log(err);
        });
    };

    $scope.remove = function (categoria) {
      Categoria.delete({id: categoria.id},
        carregarCategorias,
        function (err) {
          $scope.mensagem = {
            texto: "Não foi possive deletar a categoria, tente novamente"
          };
          console.log(err);
        });
    };

    function carregarCategorias() {
      Categoria.query(function (categorias) {
        $scope.categorias = categorias;
        $scope.mensagem = {};
      },
      function (err) {
        $scope.mensagem = {
          texto: "Não foi possivel carregar as categorias, tente atualizar a pagina"
        };
        console.log(err);
      });
    };

    carregarCategorias();

  });
