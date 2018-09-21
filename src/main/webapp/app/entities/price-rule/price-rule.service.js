(function() {
    'use strict';
    angular
        .module('artemisApp')
        .factory('PriceRule', PriceRule);

    PriceRule.$inject = ['$resource'];

    function PriceRule ($resource) {
        var resourceUrl =  'api/price-rules/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
