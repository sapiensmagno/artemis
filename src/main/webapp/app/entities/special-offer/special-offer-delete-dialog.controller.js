(function() {
    'use strict';

    angular
        .module('artemisApp')
        .controller('SpecialOfferDeleteController',SpecialOfferDeleteController);

    SpecialOfferDeleteController.$inject = ['$uibModalInstance', 'entity', 'SpecialOffer'];

    function SpecialOfferDeleteController($uibModalInstance, entity, SpecialOffer) {
        var vm = this;

        vm.specialOffer = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SpecialOffer.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
