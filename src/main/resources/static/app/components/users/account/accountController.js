angular.module('app')
.controller('AccountController', function ($routeParams, UserService){
    var vm = this;
    vm.userId = $routeParams.id ;
    vm.user = UserService.get(vm.userId);
    vm.userAdvertisements = UserService.getUserAdvertisements(vm.userId);
})