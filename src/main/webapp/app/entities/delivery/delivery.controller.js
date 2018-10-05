(function() {
    'use strict';

    angular
        .module('artemisApp')
        .controller('DeliveryController', DeliveryController);

    DeliveryController.$inject = ['Delivery'];

    function DeliveryController(Delivery) {

        var vm = this;

        vm.deliveries = [];

        loadAll();

        function loadAll() {
            Delivery.query(function(result) {
                vm.deliveries = result;
                vm.searchQuery = null;
            });
        }
    }
})();
