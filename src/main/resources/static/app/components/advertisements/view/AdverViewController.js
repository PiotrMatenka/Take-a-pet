angular.module('app')
.controller('AdverViewController', function (AdvertisementService, $routeParams, ImagesService, $cookies){
    const vm = this;
    vm.advertisementId = $routeParams.adverId;
    vm.advertisement = AdvertisementService.getViewById(vm.advertisementId);
    vm.images = ImagesService.getImagesById(vm.advertisementId);
    vm.userEmail = $cookies.get("user");
})