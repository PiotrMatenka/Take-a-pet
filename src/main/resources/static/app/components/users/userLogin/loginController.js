angular.module('app')
.controller('LoginController', function ($http, AuthService, $rootScope, $scope, $location){

    var vm = this;

    $scope.login = function () {
        // requesting the token by usename and passoword
        $http({
            url: 'user-login',
            method: "POST",
            params: {
                email: $scope.email,
                password: $scope.password
            }
        })
            .then(function success(res){
               $scope.password = null;
                // checking if the token is available in the response
                if (res.token) {
                    vm.message = '';
                    // setting the Authorization Bearer token with JWT token
                    $http.defaults.headers.common['Authorization'] = 'Bearer ' + res.token;

                    // setting the user in AuthService
                    AuthService.user = res.user;
                    $rootScope.$broadcast('LoginSuccessful');
                    $rootScope.authenticated = true;
                    // going to the home page
                    $location.path('/home');
            }
             else {
                // if the token is not present in the response then the
                // authentication was not successful. Setting the error message.
                vm.message = 'Authetication Failed !';
            }
        },function error (error) {
                vm.message = 'Authetication Failed !';
            });
            }
});