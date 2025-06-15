/**
 * Factory function to create a new measure object.
 * @param {null} userId - The id of the user.
 * @returns {Object} - A new measure object.
 */
export function createMeasure(userId = null) {
    return {
        id: null,
        userId: userId,
        description: '',
        photoUrl: '',
        location: '',
        timestamp: new Date().toISOString(),
        status: 'PENDING',
        measureType: '',
        txHash: '',
        area: null,
        capacity: null,
        files: []
    };
}

/**
 * Simple validation for a measure object.
 * Returns an array of error messages if validation fails.
 * @param {Object} measure
 * @returns {Array<string>} error messages
 */
export function validateMeasure(measure) {
    const errors = [];

    if (!measure.description?.trim()) {
        errors.push('Description is required.');
    }

    if (!measure.photoUrl) {
        errors.push('Photo is required.');
    }

    if (!measure.location?.trim()) {
        errors.push('Location is required.');
    }

    return errors;
}