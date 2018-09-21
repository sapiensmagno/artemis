(function() {
    'use strict';
    angular
        .module('artemisApp')
        .factory('SpecialOffer', SpecialOffer);

    SpecialOffer.$inject = ['$resource', 'DateUtils'];

    function SpecialOffer ($resource, DateUtils) {
        var resourceUrl =  'api/special-offers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.startDate = DateUtils.convertDateTimeFromServer(data.startDate);
                        data.endDate = DateUtils.convertDateTimeFromServer(data.endDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
