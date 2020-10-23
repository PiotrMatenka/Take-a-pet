angular.module('app')
    .constant('IMAGES_BY_ID_ENDPOINT', '/api/images/advertisement/:id')
    .factory('ImageStorage', function ($resource, IMAGES_BY_ID_ENDPOINT){
        return $resource (IMAGES_BY_ID_ENDPOINT, {id: '@_id'}, {
            getImagesById : {
                method: 'GET',
                url: IMAGES_BY_ID_ENDPOINT,
                params: {id: '@id'},
                isArray: true
            }
        });
    })
    .service('ImagesService', function (ImageStorage){
        this.getImagesById = index => ImageStorage.getImagesById({id: index});
    })