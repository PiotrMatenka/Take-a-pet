angular.module('app')
.controller('ListController', function (AdvertisementService, $routeParams){
    var vm = this;
    vm.category = $routeParams.category;
    vm.advertisements = AdvertisementService.getAllByCategory(vm.category);

})