angular.module('app')
.controller('ArchivesController', function (UserService, $routeParams){
    var vm = this;
    vm.userId = $routeParams.id;

    vm.advertisements = UserService.getEndedAdvertisements(vm.userId);

})