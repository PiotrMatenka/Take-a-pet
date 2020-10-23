angular.module('app')
.controller('AdvertisementAddController', function ($routeParams, AdvertisementService, $location, CategoryService, Advertisement, ImagesService) {
    const vm = this;
    const adverId = $routeParams.adverId;
    if (adverId){
        vm.advertisement = AdvertisementService.get(adverId);
        vm.images = ImagesService.getImagesById(adverId);}
    else
        vm.advertisement = new Advertisement();

    vm.categories = CategoryService.getAll()
    const saveCallback = () => {
        $location.path(`/ads/edit/${vm.advertisement.id}`);
        vm.msg = "Dodano ogłoszenie";
    };

    vm.advertisement.userId = 1;
    const errorCallback = err => {
        vm.msg=`Błąd zapisu: ${err.data.message}`;
    };

    vm.saveAdvertisement = () =>{
        AdvertisementService.save(vm.advertisement)
            .then(saveCallback)
            .catch(errorCallback);
    };

    vm.updateAdvertisement = () => {
        AdvertisementService.update(vm.advertisement)
            .then(saveCallback)
            .catch(errorCallback);
    }
});