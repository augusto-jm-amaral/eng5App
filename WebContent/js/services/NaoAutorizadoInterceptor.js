angular.module('eng5App')
  .factory('NaoAutorizado',
    function  ($location, $q) {

      var interceptor = {
        responseError: function (resposta) {
          if(resposta.status == 401){
            $location.path('/login');
          }

          return $q.reject(resposta);
        }
      }

      return interceptor;
    });
