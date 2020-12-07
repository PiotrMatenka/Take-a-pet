angular.module('app')
.controller('SendEmailController', function ($http, $scope){
    var vm = this;

    vm.sendEmail = function (){
        $http({
            url: 'api/userPassword/resetPassword',
            method: 'POST',
            params: {
                email: $scope.email
            }
        }).then(function success(){
            vm.successMessage = 'Wysłano nowe hasło na adres mailowy';
            $scope.email = "";
        }, function error(){
            vm.errorMessage = 'Nie znaleziono konta z podanym adresem mailowym';
            $scope.email = "";
        })
    }
})

