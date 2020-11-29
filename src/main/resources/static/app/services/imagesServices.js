angular.module('app')
    .constant('IMAGES_BY_ID_ENDPOINT', '/api/images/advertisement/:id')
    .constant('IMAGE_ENDPOINT', '/api/images/:id')
    .factory('ImageStorage', function ($resource, IMAGE_ENDPOINT, IMAGES_BY_ID_ENDPOINT){
        return $resource (IMAGE_ENDPOINT, {id: '@_id'}, {
            getImagesById : {
                method: 'GET',
                url: IMAGES_BY_ID_ENDPOINT,
                params: {id: '@id'},
                isArray: true
            }
        });
    })
    .service('ImagesService', function (ImageStorage){
        this.get = index => ImageStorage.get({id :index});
        this.getImagesById = index => ImageStorage.getImagesById({id: index});
        this.removeImage = image => image.$delete({id :image.id});
    })