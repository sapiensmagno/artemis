(function() {
    'use strict';

    angular
        .module('artemisApp')
        .controller('PurchaseDetailController', PurchaseDetailController);

    PurchaseDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Purchase', 'Delivery', 'Product', 'Supplier', 'User'];

    function PurchaseDetailController($scope, $rootScope, $stateParams, previousState, entity, Purchase, Delivery, Product, Supplier, User) {
        var vm = this;

        vm.purchase = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('artemisApp:purchaseUpdate', function(event, result) {
            vm.purchase = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
