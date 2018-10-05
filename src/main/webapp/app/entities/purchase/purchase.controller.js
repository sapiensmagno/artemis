(function() {
    'use strict';

    angular
        .module('artemisApp')
        .controller('PurchaseController', PurchaseController);

    PurchaseController.$inject = ['Purchase'];

    function PurchaseController(Purchase) {

        var vm = this;

        vm.purchases = [];

        loadAll();

        function loadAll() {
            Purchase.query(function(result) {
                vm.purchases = result;
                vm.searchQuery = null;
            });
        }
    }
})();
