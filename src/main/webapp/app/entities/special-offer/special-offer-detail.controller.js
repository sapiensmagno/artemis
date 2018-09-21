(function() {
    'use strict';

    angular
        .module('artemisApp')
        .controller('SpecialOfferDetailController', SpecialOfferDetailController);

    SpecialOfferDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SpecialOffer', 'Product'];

    function SpecialOfferDetailController($scope, $rootScope, $stateParams, previousState, entity, SpecialOffer, Product) {
        var vm = this;

        vm.specialOffer = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('artemisApp:specialOfferUpdate', function(event, result) {
            vm.specialOffer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
