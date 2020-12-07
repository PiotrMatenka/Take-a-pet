angular.module('app')
.controller('ChangePasswordController', function (UserService, $routeParams, $scope, $http){
    var vm = this;
    const userId = $routeParams.userId;
    vm.user = UserService.get(userId);
    $scope.submitted = false;
    $scope.registration= {
        'password': '',
        'confirmPassword': ''
    };

    vm.changePassword = () => {
        $scope.submitted = true;
        $http({
            url: `/api/userPassword/changePassword/${userId}`,
            method: 'POST',
            params: {
                password: $scope.registration.password
            }
        }).then(function success(){
            vm.user.password = $scope.registration.password;
            vm.msg = 'Hasło zostało zmienione';
        }, function error(){
            vm.msg = 'Nie udało się zmienić hasła';
        })
    }

    $scope.passwordChanged = function() {
        $scope.passwordRegExp = '^('+$scope.registration.confirmPassword+')$';
    };
})
