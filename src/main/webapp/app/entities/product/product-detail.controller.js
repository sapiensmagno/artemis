(function() {
    'use strict';

    angular
        .module('artemisApp')
        .controller('ProductDetailController', ProductDetailController);

    ProductDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Product', 'SpecialOffer', 'Supplier', 'PriceRule'];

    function ProductDetailController($scope, $rootScope, $stateParams, previousState, entity, Product, SpecialOffer, Supplier, PriceRule) {
        var vm = this;

        vm.product = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('artemisApp:productUpdate', function(event, result) {
            vm.product = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
